package com.yinmimoney.web.p2pnew.controller.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class AdminDto {

	private Integer id;// id，编号，为空时为增加
	@NotBlank(message = "{admin.userName.empty}")
	private String userName;// 用户名
	@NotBlank(message = "{admin.realName.empty}")
	private String realName;// 姓名
	@NotBlank(message = "{admin.mobilePhone.empty}")
	@Length(min = 11, max = 11, message = "{admin.mobilePhone.length}")
	@Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9])|(147))\\d{8}$", message = "{admin.mobilePhone.format}")
	private String mobilePhone;// 手机号码
	private String pwd;// 登录密码
	private Integer[] roleIds;// 角色id集合

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}

}