package com.yinmimoney.web.p2pnew.controller;

import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Index extends BaseController {

	@RequestMapping(value = { "/", "/api/1.0" }, method = RequestMethod.GET)
	@ResponseBody
	public ResultCode index(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		json.put("version", "1.0");
		return new ResultCode(StatusCode.SUCCESS, json);
	}




}