package com.yinmimoney.web.p2pnew.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;

@Controller
public class ErrorPageController extends BaseController {

	@RequestMapping(value = "/403")
	@ResponseBody
	public ResultCode error403() {
		return new ResultCode(StatusCode.ERROR_403);
	}

	@RequestMapping(value = "/404")
	@ResponseBody
	public ResultCode error404() {
		return new ResultCode(StatusCode.ERROR_404);
	}

	@RequestMapping(value = "/405")
	@ResponseBody
	public ResultCode error405() {
		return new ResultCode(StatusCode.ERROR_405);
	}

	@RequestMapping(value = "/500")
	@ResponseBody
	public ResultCode error500() {
		return new ResultCode(StatusCode.ERROR_500);
	}

	@RequestMapping(value = "/error")
	@ResponseBody
	public ResultCode error() {
		return new ResultCode(StatusCode.ERROR);
	}
}
