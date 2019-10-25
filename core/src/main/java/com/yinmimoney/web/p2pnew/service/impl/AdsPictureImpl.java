package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.dao.AdsPictureMapper;
import com.yinmimoney.web.p2pnew.pojo.AdsPicture;
import com.yinmimoney.web.p2pnew.service.IAdsPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;

@Service
public class AdsPictureImpl extends BaseServiceImpl<AdsPicture, AdsPictureMapper, Integer> implements IAdsPicture {
    @Autowired
    private AdsPictureMapper adsPictureMapper;

    protected AdsPictureMapper getDao() {
        return adsPictureMapper;
    }

    @Override
    public AdsPicture selectByTxid(String txid) {
        AdsPicture condi = new AdsPicture();
        condi.setTxid(txid);
        condi.setIsShow(Boolean.TRUE);
        return this.getByCondition(condi);
    }
}