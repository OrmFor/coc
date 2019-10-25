package com.yinmimoney.web.p2pnew.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController extends BaseController {

	@RequestMapping(value = "/403")
	public String error403() {
		return "403";
	}

	@RequestMapping(value = "/404")
	public String error404() {
		return "404";
	}

	@RequestMapping(value = "/405")
	public String error405() {
		return "405";
	}

	@RequestMapping(value = "/500")
	public String error500() {
		return "500";
	}

	@RequestMapping(value = "/error")
	public String error() {
		return "error";
	}
}
