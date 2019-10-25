package com.yinmimoney.web.p2pnew.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dto.BlackPermissionsDto;
import com.yinmimoney.web.p2pnew.enums.EnumBlackPermissionsType;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeNid;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeType;
import com.yinmimoney.web.p2pnew.pojo.BlackList;
import com.yinmimoney.web.p2pnew.pojo.User;
import com.yinmimoney.web.p2pnew.service.IBlackList;
import com.yinmimoney.web.p2pnew.service.IUser;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.SmsHandelUtils;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dyf on 2019/8/6 14:03
 */
@RestController("admin_blackListController")
@RequestMapping("/admin/blackList")
public class BlackListController extends BaseController {
    @Autowired
    private IBlackList blackListService;
    @Autowired
    private IUser userService;
    @Autowired
    private SmsHandelUtils smsHandelUtils;

    @RequestMapping("/getBlackPermissions")
    public ResultCode getBlackPermissions(){
        JSONObject json = getJsonFromRequest();
        String userCode = json.getString("userCode");
        if(StringUtil.isBlank(userCode)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        List<BlackPermissionsDto> blackPermissions = blackListService.getBlackPermissions(userCode);
        return new ResultCode(StatusCode.SUCCESS,blackPermissions);
    }

    @RequestMapping("/setBlackPermissions")
    public ResultCode setBlackPermissions(){
        JSONObject json = getJsonFromRequest();
        String userCode = json.getString("userCode");
        if(StringUtil.isBlank(userCode)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        User condUser = new User();
        condUser.setUserCode(userCode);
        User user = userService.getByCondition(condUser);
        if(user==null){
            return new ResultCode(StatusCode.ERROR_PARAM);
        }
        EnumBlackPermissionsType[] values = EnumBlackPermissionsType.values();
        boolean isNeedNotify=false;
        for(int i=0;i<values.length;i++){
            String nid = values[i].getNid();
            Integer integer = json.getInteger(nid);
            if(integer!=null){
                BlackList cond = new BlackList();
                cond.setPermissionsNid(nid);
                cond.setUserCode(userCode);
                BlackList blackList = blackListService.getByCondition(cond);
                //禁止
               if(integer==0){
                   isNeedNotify=true;
                   //不存在插入处理
                   if(blackList==null){
                       BlackList blackListNew = new BlackList();
                       blackListNew.setUserCode(userCode);
                       blackListNew.setPermissionsNid(nid);
                       blackListNew.setAddTime(DateUtil.getNow());
                       //0:有效  1:无效
                       blackListNew.setIsValid(0);
                       blackListService.insertSelective(blackListNew);
                   }else{
                       //无效更改
                       if(blackList.getIsValid()==1){
                           blackList.setIsValid(0);
                           blackListService.updateByPrimaryKeySelective(blackList);
                       }
                   }
               }
               //开启
               else{
                   //存在并且有效的情况下进行处理
                  if(blackList!=null&&blackList.getIsValid()==0){
                       blackList.setIsValid(1);
                       blackListService.updateByPrimaryKeySelective(blackList);
                  }
               }
            }
        }
        //发通知
        if(isNeedNotify){
            HashMap<String, Object> sendData = new HashMap();
            sendData.put("content","");
            try {
                smsHandelUtils.send(EnumNoticeNid.PERMISSION_STOP, EnumNoticeType.TYPE_MESSAGE, Constant.PLAT_CODE,userCode,
                        userCode, sendData, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResultCode(StatusCode.SUCCESS);
    }
}
