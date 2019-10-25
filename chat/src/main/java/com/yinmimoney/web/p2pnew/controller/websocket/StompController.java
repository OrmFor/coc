package com.yinmimoney.web.p2pnew.controller.websocket;

import com.yinmimoney.web.p2pnew.dto.Shout;
import com.yinmimoney.web.p2pnew.dto.ShoutResponse;
import com.yinmimoney.web.p2pnew.enums.EnumTopicType;
import com.yinmimoney.web.p2pnew.service.ICityOnChat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(StompController.class);
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
    @Autowired
    private ICityOnChat cityOnChatService;
    /**
     * 默认情况下，帧所发往的目的地会与触发处理器方法的目的地相同，只不过会添加上“/topic”前缀。
     * @param shout
     */
    @MessageMapping("/cityOnChat/speak")
    //@SendTo("/topic/speak") //可重写目的地，@MessageMapping 会发送到消息代理，客户端再从消息代理订阅
    public void stompHandleSpeak(Shout shout){
        System.out.println("haha");
        LOGGER.info("接收到消息：" + shout.getMessage());
        String cityCode = shout.getCityCode();
        String topic= EnumTopicType.TOPIC_SPEAK.getCode()+cityCode;
        ShoutResponse shoutResponse = cityOnChatService.processSpeak(shout);
        simpMessageSendingOperations.convertAndSend(topic,shoutResponse);
    }

    @MessageMapping("/cityOnChat/like")
    //@SendTo("/topic/speak") //可重写目的地，@MessageMapping 会发送到消息代理，客户端再从消息代理订阅
    public void stompHandleLike(Shout shout){
        LOGGER.info("接收到消息：" + shout.getMessage());
        String cityCode = shout.getCityCode();
        String topic=EnumTopicType.TOPIC_LIKE.getCode()+cityCode;
        ShoutResponse shoutResponse = cityOnChatService.processLike(shout);
        simpMessageSendingOperations.convertAndSend(topic,shoutResponse);
    }


    /**
     * @SubscribeMapping 的主要应用场景是实现请求-回应模式。
     * 在请求-回应模式中，客户端订阅某一个目的地，然后预期在这个目的地上获得一个一次性的响应。
     * @return
     */
    @SubscribeMapping("/getShout")
    //@SendTo("/app/marco") // @SubscribeMapping 默认直接返回给客户端,如果你加了SendTo的话则要经过代理
    public Shout getShout(){
        Shout shout = new Shout();
        shout.setMessage("Hello STOMP");
        return shout;
    }
}
