package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.util.Page;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.CityOnChat;
import com.yinmimoney.web.p2pnew.pojo.LikeLog;
import com.yinmimoney.web.p2pnew.requestbody.GetAdsPictureRequestBody;
import com.yinmimoney.web.p2pnew.responsebody.GetAdsPictureResponseBody;
import com.yinmimoney.web.p2pnew.service.ICityOnChat;
import com.yinmimoney.web.p2pnew.service.ICommonOrderDetail;
import com.yinmimoney.web.p2pnew.service.ILikeLog;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by dyf on 2019/7/19 17:04
 */
@RestController("api_CityOnChatController")
@RequestMapping("/api/1.0/cityOnChat")
public class CityOnChatController extends BaseController {
    @Autowired
    private ICityOnChat cityOnChatService;
    @Autowired
    private ILikeLog likeLogService;

    @Autowired
    private ICommonOrderDetail commonOrderDetailService;

    @RequestMapping("/list")
    public ResultCode getList(){
        JSONObject json = getJsonFromRequest();
        String cityCode = json.getString("cityCode");
        if(StringUtil.isBlank(cityCode)){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        Integer page = json.getInteger("page");
        if(page==null) {page=1;}
        CityOnChat cond = new CityOnChat();
        cond.setCityCode(cityCode);
        //该条留言是否已隐藏：0：未隐藏   1：已隐藏
        cond.setIsHidden(0);
        cond.setPageNumber(page);
        cond.setPageSize(10);
        cond.setOrderBy("add_time desc");
        Page<CityOnChat> page1 = cityOnChatService.getPage(cond, null);
        ApiToken apiToken = getApiToken();
        if(apiToken!=null) {
            page1.getResult().forEach(x -> {
                String code = x.getCode();
                String userCode = x.getUserCode();
                LikeLog likeLog = new LikeLog();
                likeLog.setChatCode(code);
                likeLog.setUserCode(apiToken.getUserCode());
                //0:有效  1:无效
                likeLog.setIsValid(0);
                LikeLog byCondition = likeLogService.getByCondition(likeLog);
                if(byCondition!=null){
                    //当前用户是否已点赞该条留言
                    x.setIsLike(1);
                }
                if(userCode.equals(apiToken.getUserCode())){
                    //是否当前用户发表的留言
                    x.setIsSelf(1);
                }
            });
        }
        return new ResultCode(StatusCode.SUCCESS,page1.getResult());
    }


    //上传照片广告位照片
    @RequestMapping("/ads/pricture")
    public ResultCode AdsPicture(){
        JSONObject json = getJsonFromRequest();
        String userCode = getApiToken().getUserCode();
        commonOrderDetailService.processAdsPicture(json,userCode);
        return new ResultCode(StatusCode.SUCCESS);
    }

    //删除照片聊天室
    @RequestMapping("/ads/deletePricture")
    public ResultCode modifyAdsPicture(){
        JSONObject json = getJsonFromRequest();
        String userCode = getApiToken().getUserCode();
        commonOrderDetailService.processModifyAdsPicture(json,userCode);
        return new ResultCode(StatusCode.SUCCESS);
    }

    //获取照片
    @RequestMapping("/city/ads/picture")
    public ResultCode getPicture(@RequestBody GetAdsPictureRequestBody getAdsPictureRequestBody,
                                 BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }
        /*String userCode = getApiToken().getUserCode();
        getAdsPictureRequestBody.setUserCode(userCode);*/
        List<GetAdsPictureResponseBody> list = commonOrderDetailService.getAdsPicture(getAdsPictureRequestBody);
        return new ResultCode(StatusCode.SUCCESS,list);
    }

}
