package com.yinmimoney.web.p2pnew.enums;

import cc.s2m.web.utils.webUtils.StaticProp;

/**
 *
 * @Description redis缓存key枚举类
 * @author wzq
 * @date 2018年6月26日 下午3:22:37
 */
public enum EnumRedisKeys {

	/** 图形验证码 */
	PIC_CODE("picCode_", "图形验证码"),

	/** 短信验证码 */
	MOBILE_CODE("mobileCode_", "短信验证码"),

	PWD_CODE("pwdCode_","登录密码")
	;

	private String key; // 前缀

	private String profile; // 备注

	private EnumRedisKeys(String key, String profile) {
		this.key = key;
		this.profile = profile;
	}

	public String getKey() {
		return StaticProp.cookieID + "_" + key;
	}

	public String getProfile() {
		return profile;
	}
}
