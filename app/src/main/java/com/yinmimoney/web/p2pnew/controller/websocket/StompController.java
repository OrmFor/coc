package com.yinmimoney.web.p2pnew.controller.websocket;

import com.yinmimoney.web.p2pnew.dto.Shout;
import com.yinmimoney.web.p2pnew.dto.ShoutResponse;
import com.yinmimoney.web.p2pnew.enums.EnumTopicType;
import com.yinmimoney.web.p2pnew.service.ICityOnChat;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by dyf on 2019/7/18 11:26
 */
@Controller
public class StompController {
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(StompController.class);

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
    @Autowired
    private ICityOnChat cityOnChatService;


    @MessageMapping("/cityOnChat/speak")
    public void stompHandleSpeak(Shout shout){
        LOGGER.info("接收到消息：" + shout.getMessage());
        String cityCode = shout.getCityCode();
        String topic= EnumTopicType.TOPIC_SPEAK.getCode()+cityCode;
        ShoutResponse shoutResponse = cityOnChatService.processSpeak(shout);
        simpMessageSendingOperations.convertAndSend(topic,shoutResponse);
    }

    @MessageMapping("/cityOnChat/like")
    public void stompHandleLike(Shout shout){
        LOGGER.info("accept message is：" + shout.getMessage());
        String cityCode = shout.getCityCode();
        String topic=EnumTopicType.TOPIC_LIKE.getCode()+cityCode;
        ShoutResponse shoutResponse = cityOnChatService.processLike(shout);
        simpMessageSendingOperations.convertAndSend(topic,shoutResponse);
    }
}
