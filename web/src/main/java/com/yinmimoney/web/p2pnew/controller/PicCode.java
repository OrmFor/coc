package com.yinmimoney.web.p2pnew.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.enums.EnumRedisKeys;

import cc.s2m.util.picCode.PicCodeUtils;

@Controller
public class PicCode extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(PicCode.class);

	@Autowired
	private PicCodeUtils picCodeUtils;

	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Set to expire far in the past.
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = picCodeUtils.createText();

		// store the text in the session
		saveCode(request, response, capText);

		// create the image with the text
		BufferedImage bi = picCodeUtils.createImage(capText);

		ServletOutputStream out = response.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);
		out.flush();
		out.close();
	}

	public void saveCode(HttpServletRequest request, HttpServletResponse response, String capText) {
		String sessionId = request.getSession(true).getId();
		LOGGER.info("图形验证码sessionId：" + sessionId);
		redisUtil.addWithTimeout(EnumRedisKeys.PIC_CODE.getKey() + sessionId, capText, 5, TimeUnit.MINUTES);
	}
}
