package com.yinmimoney.web.p2pnew.controller;

import cc.s2m.util.IpUtil;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.requestbody.RegisterRequestBody;
import com.yinmimoney.web.p2pnew.service.IApiToken;
import com.yinmimoney.web.p2pnew.service.IUser;
import com.yinmimoney.web.p2pnew.view.LoginInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/api/1.0/register")
public class RegisterController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(RegisterController.class);

	@Autowired
	private IUser userService;

    @Autowired
    private IApiToken apiTokenService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResultCode register(@Valid @RequestBody RegisterRequestBody registerRequestBody, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = getErrors(result);
            return new ResultCode(StatusCode.ERROR_LACK_PARAM, errors);
        }
		// 注册
		userService.processRegister(registerRequestBody.getUserName(), registerRequestBody.getPwd(), registerRequestBody.getEmail());
        LoginInfo loginInfo = apiTokenService.processApiToken(IpUtil.getIp(getRequest()),
                registerRequestBody.getDeviceId(), registerRequestBody.getDeviceName(),
                registerRequestBody.getEmail(), registerRequestBody.getPwd());
		return new ResultCode(StatusCode.SUCCESS,loginInfo);
	}

}
