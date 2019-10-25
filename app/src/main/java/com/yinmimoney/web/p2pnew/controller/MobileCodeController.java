//package com.yinmimoney.web.p2pnew.controller;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.yinmimoney.web.p2pnew.controller.base.BaseController;
//import com.yinmimoney.web.p2pnew.core.ResultCode;
//import com.yinmimoney.web.p2pnew.core.StatusCode;
//import com.yinmimoney.web.p2pnew.enums.EnumSendSmsCodeType;
//import com.yinmimoney.web.p2pnew.util.SendSmsCodeHandelUtils;
//
//@Controller
//@RequestMapping("/api/1.0/mobileCode")
//public class MobileCodeController extends BaseController {
//
//	private static final Logger LOGGER = LogManager.getLogger(MobileCodeController.class);
//
//	@Autowired
//	private SendSmsCodeHandelUtils sendSmsCodeHandelUtils;
//
//	@RequestMapping(method = RequestMethod.POST)
//	@ResponseBody
//	public ResultCode getMobileCode(@RequestParam(value = "mobilePhone", required = false) String mobilePhone, @RequestParam(value = "type", required = false) Integer type) {
//		LOGGER.info("mobilePhone={}，type={}", mobilePhone, type);
//		sendSmsCodeHandelUtils.sendMobileCode(mobilePhone, EnumSendSmsCodeType.getEnum(type), getIp());
//		return new ResultCode(StatusCode.SUCCESS);
//	}
//
//}
