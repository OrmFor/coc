package com.yinmimoney.web.p2pnew.pojo;

import com.yinmimoney.web.p2pnew.pojo.entity.CommonOrderEntity;

public class CommonOrder extends CommonOrderEntity {
    private static final long serialVersionUID = 1L;

    private String operatorName;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    private String operateTypeStr;
    //speak：发言  like：点赞

    public String getOperateTypeStr() {
        if (getOperateType() == null) {
            return operateTypeStr;
        }
        switch (getOperateType()) {
            case "speak":
                operateTypeStr = "发言";
                break;
            case "like":
                operateTypeStr = "点赞";
                break;
            case "house":
                operateTypeStr = "设置房间名称";
                break;
            case "Ads":
                operateTypeStr = "广告";
                break;
            case "chatroomAds":
                operateTypeStr = "聊天室广告";
                break;
            case "picture":
                operateTypeStr = "城市相册";
                break;
            case "picture_like":
                operateTypeStr = "相册点赞";
                break;
            case "city_picture_open":
                operateTypeStr="开通城市相册";
                break;
            case "city_ads_price":
                operateTypeStr="城市竞价";
                break;
            case "mod_ads_price":
                operateTypeStr="修改城市竞价";
                break;
            default:
                break;
        }
        return operateTypeStr;
    }

    public void setOperateTypeStr(String operateTypeStr) {
        this.operateTypeStr = operateTypeStr;
    }
}