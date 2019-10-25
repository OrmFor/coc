package com.yinmimoney.web.p2pnew.service.impl;

import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.dao.NoticeMapper;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeNid;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeType;
import com.yinmimoney.web.p2pnew.pojo.Notice;
import com.yinmimoney.web.p2pnew.service.INotice;
import com.yinmimoney.web.p2pnew.service.INoticeType;
import com.yinmimoney.web.p2pnew.util.RedisUtil;
import com.yinmimoney.web.p2pnew.util.SmsHandelUtils;
import com.yinmimoney.web.p2pnew.util.UserCodeGenerator;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class NoticeImpl extends BaseServiceImpl<Notice, NoticeMapper, java.lang.Integer> implements INotice {
    @Autowired
    private NoticeMapper noticeMapper;

    protected NoticeMapper getDao() {
        return noticeMapper;
    }

    @Autowired
    private UserCodeGenerator userCodeGenerator;

    @Autowired
    private SmsHandelUtils smsHandelUtils;
    @Autowired
    private INoticeType noticeTypeService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void sendEamilToResetPwd(String userCode, String email, String ip) {
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] textByte = new byte[0];
        try {
            textByte = userCode.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String code = encoder.encode(textByte);
        redisUtil.addWithTimeout(code,code,30,TimeUnit.MINUTES);
        HashMap<String,Object> sendData = new HashMap<>();
        sendData.put("url","http://127.0.0.1:81/api/plat/test?code="+code);
        sendData.put("picture","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561542444937&di=1e242d249d46d896b92e7df4d3d6eeab&imgtype=0&src=http%3A%2F%2Farticle.fd.zol-img.com.cn%2Ft_s501x2000%2Fg4%2FM09%2F06%2F02%2FCg-4zFTcU3OIVQGAAABeaY-7cT0AAUnLQFfYkcAAF6B353.jpg");
        send(userCode,email,ip,EnumNoticeNid.NID_RESET_PASSWORD,sendData,code);

    }

    @Override
    public void sendEamilToOauth(String userCode, String email, String ip) {
        String code =  userCodeGenerator.getUserCode("acti");
        redisUtil.addWithTimeout(code,code,30,TimeUnit.MINUTES);
        HashMap<String,Object> sendData = new HashMap<>();
        sendData.put("url","http://127.0.0.1:81/api/plat/test?code="+code);
        sendData.put("picture","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561542444937&di=1e242d249d46d896b92e7df4d3d6eeab&imgtype=0&src=http%3A%2F%2Farticle.fd.zol-img.com.cn%2Ft_s501x2000%2Fg4%2FM09%2F06%2F02%2FCg-4zFTcU3OIVQGAAABeaY-7cT0AAUnLQFfYkcAAF6B353.jpg");
        send(userCode,email,ip,EnumNoticeNid.NID_EMAIL_OAUTH,sendData,code);

    }

    private void send(String userCode, String email, String ip,EnumNoticeNid enumNoticeNid,
                      HashMap<String,Object> sendData,String code){
        smsHandelUtils.send(
                enumNoticeNid,
                EnumNoticeType.TYPE_EMAIL,
                Constant.PLAT_CODE,
                userCode ,
                email ,
                sendData,
                code,
                ip,
                Boolean.FALSE
        );
    }
}