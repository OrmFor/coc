package com.yinmimoney.web.p2pnew.service;

import com.yinmimoney.web.p2pnew.dto.Shout;
import com.yinmimoney.web.p2pnew.dto.ShoutResponse;
import com.yinmimoney.web.p2pnew.pojo.CityOnChat;
import cc.s2m.web.utils.webUtils.service.BaseService;

public interface ICityOnChat extends BaseService<CityOnChat, Integer> {

      /**
       *@description 处理留言
       *@param
       *@return
       */
      ShoutResponse processSpeak(Shout shout);

      /**
       *@description 处理点赞
       *@param
       *@return
       */
      ShoutResponse processLike(Shout shout);
}