package com.yinmimoney.web.p2pnew.pojo;

import com.yinmimoney.web.p2pnew.pojo.entity.OrderEntity;

public class Order extends OrderEntity {
    private static final long serialVersionUID = 1L;

    private boolean messageIsOpen;

    private String buyerName;

    private String sellerName;

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public boolean isMessageIsOpen() {
        return messageIsOpen;
    }

    public void setMessageIsOpen(boolean messageIsOpen) {
        this.messageIsOpen = messageIsOpen;
    }


}