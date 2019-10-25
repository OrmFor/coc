package com.yinmimoney.web.p2pnew.controller.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yinmimoney.web.p2pnew.util.RedisStringManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.yinmimoney.web.p2pnew.core.ResultCode;
import com.yinmimoney.web.p2pnew.core.StatusCode;
import com.yinmimoney.web.p2pnew.enums.EnumRedisKeys;
import com.yinmimoney.web.p2pnew.pojo.Admin;
import com.yinmimoney.web.p2pnew.pojo.AdminActions;
import com.yinmimoney.web.p2pnew.pojo.ApiToken;
import com.yinmimoney.web.p2pnew.pojo.SysLog;
import com.yinmimoney.web.p2pnew.service.IAdminActions;
import com.yinmimoney.web.p2pnew.service.ISysLog;
import com.yinmimoney.web.p2pnew.util.DateUtil;
import com.yinmimoney.web.p2pnew.util.RedisUtil;

import cc.s2m.util.CookieUtil;
import cc.s2m.util.HtmlParsePlaintText;
import cc.s2m.util.IpUtil;
import cc.s2m.web.utils.webUtils.StaticProp;

public class BaseController {

	private static final Logger LOGGER = LogManager.getLogger(BaseController.class);
	@Autowired
	protected RedisStringManager redisStringManager;
	@Autowired
	protected RedisUtil redisUtil;
	@Autowired
	private IAdminActions adminActionsService;
	@Autowired
	private ISysLog sysLogService;

	public static Map<String, String> getErrors(BindingResult result) {
		Map<String, String> map = Maps.newHashMap();
		List<FieldError> list = result.getFieldErrors();
		for (FieldError error : list) {
			map.put(error.getField(), error.getDefaultMessage());
		}
		return map;
	}

	public void updateRequestLimit(String key, String value,int minute) {
		// 保存第一次发送验证码，并且置过期时间
		if (Strings.isNullOrEmpty(value))
			redisStringManager.addWithTimeout(key, "1", minute, TimeUnit.MINUTES);
		else
			// 每发送一次增加一次记录
			redisStringManager.incr(key);
	}

	public boolean validateRequestLimit(String key,int num){
		String value = redisStringManager.find(key);// 目前已经发送次数
		if (!Strings.isNullOrEmpty(value)) {
			if (Integer.parseInt(value) > num) {
				return false;
			}
		}
		return true;
	}

	public ResultCode validateSendMobileCodeLimit(String key) {
		String value = redisUtil.find(key);// 目前已经发送次数
		if (!Strings.isNullOrEmpty(value)) {
			if (Integer.parseInt(value) > 4) {
				return new ResultCode(StatusCode.ERROR_MOBILE_CODE_SEND_LIMIT);
			}
		}
		return new ResultCode(StatusCode.SUCCESS, value);
	}

	public void updateSendMobileCodeLimit(String key, String value) {
		// 保存第一次发送验证码，并且置过期时间
		if (Strings.isNullOrEmpty(value))
			redisUtil.addWithTimeout(key, "1", 20, TimeUnit.MINUTES);
		else
			// 每发送一次增加一次记录
			redisUtil.incr(key);
	}

	public boolean checkMobileCodeIsEqual(String mobile, String code) {
		String code_error = (String) redisUtil.find(EnumRedisKeys.MOBILE_CODE + mobile + "_error");
		if (!Strings.isNullOrEmpty(code_error)) {
			long errorNumber = Long.parseLong(code_error);
			if (errorNumber >= 5) {
				// 错误次数上限
				redisUtil.delete(EnumRedisKeys.MOBILE_CODE + mobile);
				redisUtil.delete(EnumRedisKeys.MOBILE_CODE + mobile + "_error");
				return false;
			}
		}
		String code_ = (String) redisUtil.find(EnumRedisKeys.MOBILE_CODE + mobile);
		if (Strings.isNullOrEmpty(code_)) {
			return false;
		}
		if (!code_.equalsIgnoreCase(code)) {
			// 增加错误次数
			redisUtil.addWithTimeout(EnumRedisKeys.MOBILE_CODE + mobile + "_error", Strings.isNullOrEmpty(code_error) ? "1" : code_error, 30, TimeUnit.MINUTES);
			return false;
		}
		return true;
	}

	public boolean checkCodeIsEqual(HttpServletRequest request, String picCode) {
		if (picCode == null || picCode.trim().isEmpty()) {
			return false;
		}
		String sessionId = request.getSession(true).getId();
		LOGGER.info("登录图形验证码sessionId：" + sessionId);
		String redisPicCode = (String) redisUtil.find(EnumRedisKeys.PIC_CODE.getKey() + sessionId);
		redisUtil.delete(EnumRedisKeys.PIC_CODE.getKey() + sessionId);
		if (redisPicCode == null || redisPicCode.trim().isEmpty()) {
			return false;
		}
		if (!picCode.equalsIgnoreCase(redisPicCode)) {
			return false;
		}
		return true;
	}

	public boolean checkCodeIsEqual(HttpServletRequest request, HttpServletResponse response, String picCode) {
		if (picCode == null || picCode.trim().isEmpty()) {
			return false;
		}
		// String sessionId = request.getSession(true).getId();
		CookieUtil cookie = new CookieUtil(request, response);
		String sessionId = cookie.getCookie(StaticProp.cookieID);
		LOGGER.info("登录图形验证码sessionId：" + sessionId);
		String redisPicCode = (String) redisUtil.find(EnumRedisKeys.PIC_CODE.getKey() + sessionId);
		redisUtil.delete(EnumRedisKeys.PIC_CODE.getKey() + sessionId);
		if (redisPicCode == null || redisPicCode.trim().isEmpty()) {
			return false;
		}
		if (!picCode.equalsIgnoreCase(redisPicCode)) {
			return false;
		}
		return true;
	}

	public Map<String, String> upload(MultipartFile upfile, boolean isLocal, String prefixPath, boolean skipCheck, boolean useOtherName) {
		Map<String, String> resultJson = new HashMap<String, String>();
		if (upfile == null || upfile.getSize() < 1) {
			resultJson.put("msg", "未包含文件上传域");
			return resultJson;
		}
		String fileName = upfile.getOriginalFilename();
		if (!skipCheck) {
			Iterator<String> type = Arrays.asList(StaticProp.allowFiles).iterator();
			boolean allowFilesFlag = false;
			while (type.hasNext()) {
				String ext = type.next();
				if (fileName.toLowerCase().endsWith(ext)) {
					allowFilesFlag = true;
				}
			}
			if (!allowFilesFlag) {
				resultJson.put("msg", "不允许的文件格式");
				return resultJson;
			}
		}
		String otherName = "";
		if (useOtherName)
			otherName = DigestUtils.md5Hex(UUID.randomUUID().toString());
		else
			otherName = fileName.substring(0, fileName.lastIndexOf("."));
		String url = prefixPath + DateUtil.dateStr7(DateUtil.getNow()) + "/" + otherName;
		url += fileName.substring(fileName.lastIndexOf("."));
		if (isLocal) {
			try {
				url = "/upload/" + url;
				LOGGER.info("本地url：" + url);
				File file = new File(StaticProp.SERVLET_CONTEXT.getRealPath(url));
				Files.createParentDirs(file);
				Files.write(upfile.getBytes(), file);
			} catch (Exception e) {
				e.printStackTrace();
				resultJson.put("msg", e.getMessage());
				return resultJson;
			}
		} else {
			try {
				LOGGER.info("又拍云url：" + url);
				boolean a = StaticProp.UP_YUN.writeFile(url, upfile.getBytes(), true);
				LOGGER.info("上传结果：" + a);
				if (!a) {
					resultJson.put("msg", "上传到upyun失败");
					return resultJson;
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultJson.put("msg", e.getMessage());
				return resultJson;
			}
		}
		resultJson.put("code", "0");
		resultJson.put("msg", "SUCCESS");
		resultJson.put("size", String.valueOf(upfile.getSize()));
		resultJson.put("originalName", fileName);
		resultJson.put("name", url);
		if (isLocal) {
			resultJson.put("url", url);
		} else {
			String upyunDomain = StaticProp.sysConfig.get("upyun.domain");
			if (!Strings.isNullOrEmpty(upyunDomain) && upyunDomain.endsWith("/")) {
				upyunDomain = upyunDomain.substring(0, upyunDomain.length() - 1);
			}
			resultJson.put("url", upyunDomain + "/" + url);
		}
		resultJson.put("type", fileName.substring(fileName.lastIndexOf(".")));
		return resultJson;
	}

	public Map<String, String> uploadFromUrl(String remoteUrl, String ext) {
		Map<String, String> resultJson = new HashMap<String, String>();
		if (Strings.isNullOrEmpty(remoteUrl)) {
			resultJson.put("msg", "远程地址不能为空");
			return resultJson;
		}
		List<String> types = Arrays.asList(StaticProp.allowFiles);
		if (!types.contains(ext)) {
			resultJson.put("msg", "不允许的文件格式");
			return resultJson;
		}
		String url = StaticProp.upYunPath + DigestUtils.md5Hex(UUID.randomUUID().toString());
		url += ext;
		byte[] bytes = null;
		List<Byte> byteList = Lists.newArrayList();
		try {
			InputStream inputStream = new URL(remoteUrl).openStream();
			int b;
			while ((b = inputStream.read()) != -1) {
				byteList.add((byte) b);
			}
			bytes = new byte[byteList.size()];
			for (int i = 0; i < byteList.size(); i++) {
				bytes[i] = byteList.get(i);
			}
			boolean a = StaticProp.UP_YUN.writeFile(url, bytes, true);
			if (!a) {
				resultJson.put("msg", "上传到upyun失败");
				return resultJson;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultJson.put("msg", e.getMessage());
			return resultJson;
		}
		resultJson.put("msg", "SUCCESS");
		resultJson.put("size", String.valueOf(bytes.length));
		resultJson.put("originalName", remoteUrl);
		resultJson.put("name", url);
		resultJson.put("url", "http://" + StaticProp.sysConfig.get("upyun.domain") + "/" + url);
		resultJson.put("type", ext);
		return resultJson;
	}

	public String changeTextAreaToHtml(String textArea) {
		return HtmlParsePlaintText.changeTextAreaToHtml(textArea);
	}

	public ApiToken getApiToken() {
		return (ApiToken) getRequest().getAttribute("apiUser");
	}

	public Admin getSessionAdmin() {
		return (Admin) getRequest().getAttribute("sessionAdmin");
	}

	public Admin getMjsAdmin() {
		return (Admin) getRequest().getAttribute("mjsAdmin");
	}

	public String getIp() {
		return IpUtil.getIp(getRequest());
	}

	@InitBinder
	public void setDisallowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("pageNumber", "pageSize", "pageBeginIndex", "expressionChainList", "orderBy");
	}

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	public JSONObject getJsonFromRequest() {
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = request.getReader();
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} finally {
				reader.close();
			}
			LOGGER.info("参数：" + builder);
			if (Strings.isNullOrEmpty(builder.toString())) {
				builder.append("{}");
				// throw new RuntimeException("data empty");
			}
			return JSONObject.parseObject(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String readReqStr(HttpServletRequest request) {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("参数：" + sb);
		return sb.toString();
	}

	public void addSysLog(final String module, final String oprate, final String msg) {
		String uri = getRequest().getRequestURI().trim();
		uri = uri.trim();
		if (uri.endsWith("/") && uri.length() > 1) {
			uri = uri.substring(0, uri.length() - 1);
		}
		final String uri_ = uri;
		AdminActions condition = new AdminActions();
		condition.setUrl(uri_);
		AdminActions adminActions = adminActionsService.getByCondition(condition);
		if (adminActions == null) {
			adminActions = new AdminActions();
			adminActions.setName("-");
		}
		final AdminActions adminActions_ = adminActions;
		final Admin admin = getSessionAdmin();
		final String ip = getIp();
		StaticProp.execute.execute(new Runnable() {
			public void run() {
				SysLog sysLog = new SysLog();
				sysLog.setAdminId(admin.getId());
				sysLog.setIp(ip);
				sysLog.setModuleType(module);
				sysLog.setOprateType(oprate);
				sysLog.setMsg(msg);
				sysLog.setName(adminActions_.getName());
				sysLog.setUri(uri_);
				sysLogService.insertSelective(sysLog);
			}
		});
	}

}
