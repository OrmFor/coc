package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.pojo.AdsPicture;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface IAdsPicture extends BaseService<AdsPicture, Integer> {
       AdsPicture selectByTxid(String txid);
}