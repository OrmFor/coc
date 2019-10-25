package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.pojo.User;
import cc.s2m.web.utils.webUtils.service.BaseService;
import com.yinmimoney.web.p2pnew.requestbody.ResetPwdResquestBody;
import com.yinmimoney.web.p2pnew.requestbody.SaveAddressAndPriceRequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IUser extends BaseService<User, Integer> {
    void processRegister(String userName, String pwd, String email);

    User selectByUserName(String userName);

    User selectByEmail(String email);

    boolean checkUserExist(String userName,String eamil);

    User selectByUserCode(String userCode);

    void modifyAddress(String pwd, String walletAddress, String userCode);

    void saveAddressAndPrice(SaveAddressAndPriceRequestBody body);

    ResultCode processResetPwd(ResetPwdResquestBody body) ;

    ResultCode processOauth(String code, String userCode);

    ResultCode processOauth(HttpServletRequest request, HttpServletResponse response);
}