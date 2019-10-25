package com.yinmimoney.web.p2pnew.pojo;

import com.yinmimoney.web.p2pnew.pojo.entity.SConfigEntity;

public class SConfig extends SConfigEntity {
    private static final long serialVersionUID = 1L;

	private String statusStr;// 状态

	public String getStatusStr() {
		if (getStatus() == null) {
			return statusStr;
		}
		switch (getStatus()) {
		case 0:
			statusStr = "禁用";
			break;
		case 1:
			statusStr = "启用";
			break;
		default:
			statusStr = "-";
			break;
		}
		return statusStr;
	}
}