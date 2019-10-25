package com.yinmimoney.web.p2pnew.pojo;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.pojo.entity.MessageEntity;
import com.yinmimoney.web.p2pnew.util.JSONFiledUtils;
import com.yinmimoney.web.p2pnew.util.StringUtil;

public class Message extends MessageEntity {
    private static final long serialVersionUID = 1L;

    private JSONObject jsonContent;

    public JSONObject getJsonContent() {
        String content = getContent();
        JSONObject jsonObject =new JSONObject();
        if(StringUtil.isNotBlank(content)){
            try {
                jsonObject=JSONObject.parseObject(content);
                String contentback = jsonObject.getString("content");
                jsonObject.put("content", JSONFiledUtils.backSensitiveChar(contentback));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public void setJsonContent(JSONObject jsonContent) {
        this.jsonContent = jsonContent;
    }
}