package com.yinmimoney.web.p2pnew.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yinmimoney.web.p2pnew.controller.base.BaseController;

@Controller
public class UmeditorUpload extends BaseController {

	@RequestMapping(value = "/plupload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> plupload(@RequestParam MultipartFile file // 上传的文件
	) {
		return upload(file, false, "quickmovie/pic/", false, true);
	}

	@RequestMapping(value = "/pluploadLocal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> pluploadLocal(@RequestParam MultipartFile file // 上传的文件
	) {
		return upload(file, true, "quickmovie/pic/", false, true);
	}

	@RequestMapping(value = "/pluploadApk", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> pluploadApk(@RequestParam MultipartFile file // 上传的文件
	) {
		return upload(file, false, "quickmovie/apk/", true, false);
	}
}
