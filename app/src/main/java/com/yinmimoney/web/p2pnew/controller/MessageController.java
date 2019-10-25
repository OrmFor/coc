package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.util.Page;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumMessageStatus;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeNid;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.Message;
import com.yinmimoney.web.p2pnew.service.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

/**
 * Created by dyf on 2019/7/22 11:49
 */
@RestController("api_messageController")
@RequestMapping("/api/1.0/message")
public class MessageController extends BaseController{
    @Autowired
    private IMessage messageService;

    @RequestMapping("/list")
    public ResultCode getList(){
        JSONObject json = getJsonFromRequest();
        ApiToken apiToken = getApiToken();
        Message messageCond = new Message();
        messageCond.and(Expressions.in("nid", Lists.newArrayList(EnumNoticeNid.GIVE_A_LIKE.getNid(),
                EnumNoticeNid.LEAVE_A_MESSAGE.getNid(),EnumNoticeNid.PICTURE_LIKE.getNid(),EnumNoticeNid.SUCCESS_BUY.getNid(),
                EnumNoticeNid.ADS_PICTURE_HIDDEN.getNid(),EnumNoticeNid.CHAT_CONTENT_HIDDEN.getNid(),EnumNoticeNid.CITY_PICTURE_HIDDEN.getNid(),
                EnumNoticeNid.PERMISSION_STOP.getNid())));
        messageCond.setReceiveUser(apiToken.getUserCode());
        Integer page = json.getInteger("pageNumber");
        if(page==null){page=1;}
        Integer pageSize=10;
        messageCond.setPageNumber(page);
        messageCond.setPageSize(pageSize);
        Page<Message> pageBean = messageService.getPage(messageCond,null);
        JSONObject result = new JSONObject();
        result.put("list",pageBean.getResult());
        result.put("pageNumber",pageBean.getCurPage());
        result.put("totalPage",pageBean.getTotalPage());
        result.put("totalCount",pageBean.getTotalRow());
        return new ResultCode(StatusCode.SUCCESS,result);
    }

    @RequestMapping("/like/notRead/count")
    public ResultCode getNotReadNumber(){
        ApiToken apiToken = getApiToken();
        Message messageCond = new Message();
        messageCond.and(Expressions.in("nid", Lists.newArrayList(EnumNoticeNid.GIVE_A_LIKE.getNid(),
                EnumNoticeNid.LEAVE_A_MESSAGE.getNid(),EnumNoticeNid.PICTURE_LIKE.getNid(),EnumNoticeNid.SUCCESS_BUY.getNid(),
                EnumNoticeNid.ADS_PICTURE_HIDDEN.getNid(),EnumNoticeNid.CHAT_CONTENT_HIDDEN.getNid(),EnumNoticeNid.CITY_PICTURE_HIDDEN.getNid(),
                EnumNoticeNid.PERMISSION_STOP.getNid())));
        messageCond.setReceiveUser(apiToken.getUserCode());
        messageCond.setStatus(EnumMessageStatus.NO_READ.getStatus());
        int count = messageService.getCount(messageCond);
        return new ResultCode(StatusCode.SUCCESS,count);
    }

    @RequestMapping("/set/hasRead")
    public ResultCode setHasRead(){
        ApiToken apiToken = getApiToken();
        Message message = new Message();
        Message update=new Message();
        Message cond = new Message();
        message.setStatus(EnumMessageStatus.NO_READ.getStatus());
        message.setReceiveUser(apiToken.getUserCode());
        List<Message> list = messageService.getList(message);
        list.forEach(x->{
           cond.setId(x.getId());
           update.setStatus(EnumMessageStatus.HAS_READ.getStatus());
           messageService.updateByCondition(update,cond);
        });
        return new ResultCode(StatusCode.SUCCESS);
    }
}
