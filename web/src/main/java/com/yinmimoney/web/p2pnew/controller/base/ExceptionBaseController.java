package com.yinmimoney.web.p2pnew.controller.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinmimoney.web.p2pnew.exception.BussinessException;

@ControllerAdvice
public class ExceptionBaseController {

	private static final Logger LOGGER = LogManager.getLogger(ExceptionBaseController.class);

	@ExceptionHandler(BussinessException.class)
	@ResponseBody
	public String handleBussinessException(BussinessException ex) {
		LOGGER.error("BussinessException", ex);
		return ex.getMessage();
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		LOGGER.error("HttpRequestMethodNotSupportedException", ex);
		return "请求方法不支持";
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleException(Exception ex) {
		LOGGER.error("Exception", ex);
		return ex.getMessage();
	}
}
