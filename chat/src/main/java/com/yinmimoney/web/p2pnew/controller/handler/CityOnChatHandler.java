package com.yinmimoney.web.p2pnew.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * Created by dyf on 2019/7/17 15:47
 */
public class CityOnChatHandler extends AbstractWebSocketHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CityOnChatHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        LOGGER.info("WebSocket 连接建立......");
        System.out.println("haha");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        LOGGER.info("接收到消息：" + message.getPayload());
        System.out.println("接收到消息:"+message.getPayload());
        Thread.sleep(2000);
        //发送文本消息
        session.sendMessage(new TextMessage("Polo!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus){
        LOGGER.info("WebSocket 连接关闭......");
    }
}
