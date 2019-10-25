package com.yinmimoney.web.p2pnew.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.yinmimoney.web.p2pnew.constant.Constant;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumApiTokenStatus;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeNid;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeStatus;
import com.yinmimoney.web.p2pnew.enums.EnumNoticeType;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.pojo.*;
import com.yinmimoney.web.p2pnew.service.INotice;
import com.yinmimoney.web.p2pnew.service.INoticeType;
import com.yinmimoney.web.p2pnew.service.IPlatform;
import com.yinmimoney.web.p2pnew.service.IUser;
import com.yinmimoney.web.p2pnew.util.RedisUtil;
import com.yinmimoney.web.p2pnew.util.StringUtil;
import com.yinmimoney.web.p2pnew.util.UserCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/plat")
public class PlatController extends BaseController {

    @Autowired
    private IPlatform platformService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IUser userService;

    @Autowired
    private INoticeType noticeTypeService;
    @Autowired
    private INotice noticeService;
    @Autowired
    private UserCodeGenerator userCodeGenerator;

    @RequestMapping("/info")
    public ResultCode info(){
        Platform platform = platformService.getByPlatCode(Constant.PLAT_CODE);
        return new ResultCode(StatusCode.SUCCESS, platform);
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public ResultCode test(HttpServletRequest request){
        String code = request.getParameter("code");
        Boolean exist = redisUtil.exist(code);
        if (!exist){
            return  new ResultCode("500","expire");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        String userCode = null;
        try {
            userCode = new String(decoder.decodeBuffer(code), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = userService.selectByUserCode(userCode);
        if(user == null){
            throw new BussinessException(StatusCode.ERROR_ACCOUNT_NOT_EXIST);
        }

        redisUtil.delete(code);

        return new ResultCode(StatusCode.SUCCESS);
    }



    @RequestMapping(value = "sendEmail")
    public ResultCode sendEmail(){
        JSONObject json = getJsonFromRequest();
        String email = json.getString("email");
        NoticeType condition = new NoticeType();
        condition.setNid(EnumNoticeNid.NID_RESET_PASSWORD.getNid());
        condition.setNoticeType(EnumNoticeType.TYPE_EMAIL.getType());
        NoticeType smsType = noticeTypeService.getByCondition(condition);
        if (smsType == null) {
            throw new BussinessException(StatusCode.ERROR_NOTIC_TYPE_NOT_CONFIG);
        }


        String code =  userCodeGenerator.getUserCode("acti");
        redisUtil.addWithTimeout(code,code,30,TimeUnit.MINUTES);
        HashMap<String,Object> sendData = new HashMap<>();
        sendData.put("url","http://127.0.0.1:81/api/plat/test?code="+code);
        sendData.put("picture","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561542444937&di=1e242d249d46d896b92e7df4d3d6eeab&imgtype=0&src=http%3A%2F%2Farticle.fd.zol-img.com.cn%2Ft_s501x2000%2Fg4%2FM09%2F06%2F02%2FCg-4zFTcU3OIVQGAAABeaY-7cT0AAUnLQFfYkcAAF6B353.jpg");

        Notice notice = new Notice();
        notice.setNid(EnumNoticeNid.NID_MODIFY_PASSWORD.getNid());
        notice.setType(EnumNoticeType.TYPE_EMAIL.getType());
        notice.setSentUser("wwylrj123@163.com");
        notice.setReceiveUser("1449213506@qq.com");
        notice.setReceiveAddr("1449213506@qq.com");
        notice.setStatus(EnumNoticeStatus.STATUS_UNSEND.getStatus());
        notice.setTitle(smsType.getTitleTemplet());
        notice.setContent(StringUtil.fillTemplet(smsType.getTemplet(), sendData));
        notice.setCode("123");
        noticeService.insertSelective(notice);


        // 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");// 连接协议
        props.setProperty("mail.smtp.host", "smtp.163.com");// 连接协议
        props.setProperty("mail.smtp.port", "25");// 连接协议
        // 指定验证为true
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.timeout","1000");
        // 验证账号及密码，密码需要是第三方授权码
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("wwylrj123@qq.com", "qwertyuiop123456");
            }
        };
        Session session = Session.getInstance(props, auth);
        // 2.创建一个Message，它相当于是邮件内容
        MimeMessage message = new MimeMessage(session);
        try {
            //防止成为垃圾邮件，披上outlook的马甲
            message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
            // 设置发送者
            message.setFrom(new InternetAddress("wwylrj123@qq.com"));
            // 设置发送方式与接收者
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            // 设置主题
            message.setSubject(notice.getTitle());
            //创建消息主体
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(notice.getContent());

            // 创建多重消息
            Multipart multipart=new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            // 设置邮件消息发送的时间
            message.setSentDate(Calendar.getInstance().getTime());
            // 设置内容
            message.setContent(multipart, "text/html;charset=utf-8");
            // 3.创建 Transport用于将邮件发送
            Transport.send(message);

        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResultCode(StatusCode.SUCCESS);
    }



}
