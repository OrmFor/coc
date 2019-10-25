package com.yinmimoney.web.p2pnew.pojo;

import com.yinmimoney.web.p2pnew.pojo.entity.NoticeTypeEntity;

public class NoticeType extends NoticeTypeEntity {
	private static final long serialVersionUID = 1L;

	private String noticeTypeStr;

	public String getNoticeTypeStr() {
		if (getNoticeType() == null) {
			return noticeTypeStr;
		}
		switch (getNoticeType()) {
		case 1:
			noticeTypeStr = "短信";
			break;
		case 2:
			noticeTypeStr = "邮件";
			break;
		case 3:
			noticeTypeStr = "站内信";
			break;
		default:
			break;
		}
		return noticeTypeStr;
	}
}