package com.yinmimoney.web.p2pnew.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.pojo.VersionUpManage;
import com.yinmimoney.web.p2pnew.service.IVersionUpManage;
import com.yinmimoney.web.p2pnew.view.VersionInfo;

@Controller
@RequestMapping("/api/1.0/version")
public class VersionUpManageController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(VersionUpManageController.class);

	@Autowired
	private IVersionUpManage versionUpManageService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResultCode version(@RequestParam(value = "channel", required = false) String channel) {
		LOGGER.info("channel={}", channel);
		if (Strings.isNullOrEmpty(channel) ) {
			throw new BussinessException(StatusCode.ERROR_LACK_PARAM);
		}
		VersionUpManage cond = new VersionUpManage();
		cond.setChannel(channel);
		cond.setOrderBy("version_code DESC");
		VersionUpManage versionUpManage = versionUpManageService.getByCondition(cond);
		VersionInfo versionInfo = new VersionInfo();
		if (versionUpManage != null) {
			versionInfo.setVersionCode(versionUpManage.getVersionCode());
			versionInfo.setVersionName(versionUpManage.getVersionNumber());
			versionInfo.setVersionLevel(versionUpManage.getVersionLevel());
			versionInfo.setUpdateUrl(versionUpManage.getUpdateUrl());
			versionInfo.setContent(versionUpManage.getVersionContent());
		}
		return new ResultCode(StatusCode.SUCCESS, versionInfo);
	}

}
