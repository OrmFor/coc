package com.yinmimoney.web.p2pnew.service.impl;

import cc.s2m.web.utils.webUtils.service.BaseServiceImpl;
import cc.s2m.web.utils.webUtils.util.AccountDigestUtils;
import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.dao.UserMapper;
import com.yinmimoney.web.p2pnew.enums.EnumApiTokenStatus;
import com.yinmimoney.web.p2pnew.enums.EnumCitySellStatus;
import com.yinmimoney.web.p2pnew.enums.EnumRedisKeys;
import com.yinmimoney.web.p2pnew.enums.EnumUserStatus;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.CityDetail;
import com.yinmimoney.web.p2pnew.pojo.User;
import com.yinmimoney.web.p2pnew.requestbody.ResetPwdResquestBody;
import com.yinmimoney.web.p2pnew.requestbody.SaveAddressAndPriceRequestBody;
import com.yinmimoney.web.p2pnew.service.IApiToken;
import com.yinmimoney.web.p2pnew.service.ICityDetail;
import com.yinmimoney.web.p2pnew.service.IUser;
import com.yinmimoney.web.p2pnew.util.MailCheckUtil;
import com.yinmimoney.web.p2pnew.util.RedisUtil;
import com.yinmimoney.web.p2pnew.util.UserCodeGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class UserImpl extends BaseServiceImpl<User, UserMapper, Integer> implements IUser {
    @Autowired
    private UserMapper userMapper;

    protected UserMapper getDao() {
        return userMapper;
    }

    @Autowired
    private UserCodeGenerator userCodeGenerator;

    @Autowired
    protected RedisUtil redisUtil;
    @Autowired
    private ICityDetail cityDetailService;
    @Autowired
    private IApiToken apiTokenService;

    @Override
    public void processRegister(String userName, String pwd, String email) {
        if (!MailCheckUtil.isEmail(email)) {
            throw new BussinessException(StatusCode.ERROR_USER_EMAIL_FORMAT);

        }

        if (selectByUserName(userName) != null) {
            throw new BussinessException(StatusCode.ERROR_USER_NAME_EXIST);
        }
        if (selectByEmail(email) != null) {
            throw new BussinessException(StatusCode.ERROR_USER_EMAIL_EXIST);
        }
        //生成userCode
        String userCode = userCodeGenerator.getUserCode("user_");
        User user = new User();
        //user.setEmail(email);
        //user.setPwd(AccountDigestUtils.getMd5Pwd(userCode, pwd));
        user.setUserName(userName);
        user.setUserCode(userCode);
        //user.setStatus(EnumUserStatus.STATUS_REGISTER.getStatus());

        this.insert(user);
    }


    @Override
    public User selectByUserName(String userName) {
        User user = new User();
        user.setUserName(userName);
        return this.getByCondition(user);
    }

    @Override
    public User selectByEmail(String email) {
        User user = new User();
      //  user.setEmail(email);
        return this.getByCondition(user);
    }

    public boolean checkUserExist(String userName, String eamil) {
        return this.selectByEmail(eamil) != null
                || this.selectByUserName(userName) != null;
    }

    @Override
    public User selectByUserCode(String userCode) {
        User user = new User();
        user.setUserCode(userCode);
        return this.getByCondition(user);
    }

    @Override
    public void modifyAddress(String pwd, String walletAddress, String userCode) {
       /* User user = selectByUserCode(userCode);
        String md5Pwd = AccountDigestUtils.getMd5Pwd(userCode, pwd).toUpperCase();
        String userPwd = user.getPwd();
        if (!userPwd.toUpperCase().equals(md5Pwd)) {
            String code_error = (String) redisUtil.find(EnumRedisKeys.PWD_CODE + user.getEmail() + "_error");
            if (!Strings.isNullOrEmpty(code_error)) {
                long errorNumber = Long.parseLong(code_error);
                if (errorNumber >= 5) {
                    throw new BussinessException(StatusCode.ERROR_PWD_SEND_LIMIT);
                }
            }
            redisUtil.addWithTimeout(EnumRedisKeys.PWD_CODE + user.getEmail() + "_error",
                    Strings.isNullOrEmpty(code_error) ? "1" : (Long.parseLong(code_error) + 1) + "", 30, TimeUnit.MINUTES);

            throw new BussinessException(StatusCode.ERROR_PWD_VALIDATE);

        }


        User update = new User();
        update.setWalletAddress(walletAddress);
        User condi = new User();
        condi.setUserCode(userCode);
        updateByCondition(update, condi);*/
    }

    @Override
    public void saveAddressAndPrice(SaveAddressAndPriceRequestBody body) {
       /* User update = new User();
       // update.setWalletAddress(body.getWalletAddress());
        User condi = new User();
        condi.setUserCode(body.getUserCode());
        updateByCondition(update,condi);*/
        CityDetail detail = new CityDetail();
        detail.setCitySellStatus(EnumCitySellStatus.SELL.getStatus());
        detail.setAmount(body.getAmount());
        detail.setCurrency(body.getCurrency());
        CityDetail cond = new CityDetail();
        cond.setUserCode(body.getUserCode());
        cond.setCityCode(body.getCityCode());
        cityDetailService.updateByCondition(detail,cond);

    }

    @Override
    public ResultCode processResetPwd(ResetPwdResquestBody body)  {
        Boolean exist = redisUtil.exist(body.getCode());
        if (!exist){
            return  new ResultCode("500","expire");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        String userCode = null;
        try {
            userCode = new String(decoder.decodeBuffer(body.getCode()), "UTF-8");
        } catch (IOException e) {
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        User user = selectByUserCode(userCode);
        if(user == null){
            return new ResultCode(StatusCode.ERROR_LACK_PARAM);
        }
        User update = new User();
      //  update.setPwd(AccountDigestUtils.getMd5Pwd(user.getUserCode(), body.getPwd()));
       // update.setStatus(EnumUserStatus.STATUS_OAUTH.getStatus());
        User condi = new User();
        condi.setUserCode(user.getUserCode());
        updateByCondition(update, condi);
        // 查找有效token
        ApiToken condition = new ApiToken();
        condition.setUserCode(user.getUserCode());
        condition.setStatus(EnumApiTokenStatus.STATUS_NORMAL.getStatus());
        ApiToken apiToken = apiTokenService.getByCondition(condition);
        // 更新token状态为过期
        apiTokenService.updateApiTokenStatus(apiToken, EnumApiTokenStatus.STATUS_DISABLED);
        redisUtil.delete(body.getCode());
        return new ResultCode(StatusCode.SUCCESS);
    }

    @Override
    public ResultCode processOauth(String code, String userCode) {
        Boolean exist = redisUtil.exist(code);
        if (!exist){
            return  new ResultCode("500","expire");
        }
        User user = selectByUserCode(userCode);
      //  user.setStatus(EnumUserStatus.STATUS_OAUTH.getStatus());
        User condi = new User();
        condi.setUserCode(user.getUserCode());
        updateByCondition(user, condi);
        redisUtil.delete(code);
        return new ResultCode(StatusCode.SUCCESS);

    }

    @Override
    public ResultCode processOauth(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String auth = (String) session.getAttribute("auth");

        if (StringUtils.isBlank(auth) || "undefined".equals(auth)) {
            return new ResultCode(StatusCode.ERROR_OAUTH_TOKEN_NOT_EXISTS);
        }

        User user = selectByUserCode(auth);

        if(user == null){
           // insert()
        }else{
            User userUpdate = new User();
            userUpdate.setUpdateTime(new Date());
        }


        return new ResultCode(StatusCode.SUCCESS);
    }


}