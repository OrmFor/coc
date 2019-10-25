package com.yinmimoney.web.p2pnew.enums;

/**
 * 
 * @Description 配置枚举类
 * @author wzq
 * @date 2018年6月27日 上午11:50:12
 */
public enum EnumConfig {

	/** app登录token过期时间例 **/
	APP_EXPIRED_TIMES("APP_EXPIRED_TIMES", "app登录token过期时间"),
	/** 购买视频回调绑定ip **/
	NOTIFY_BIND_IP("NOTIFY_BIND_IP", "购买视频回调绑定ip");

	private String key;

	private String name;

	private EnumConfig(String key, String name) {
		this.key = key;
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
