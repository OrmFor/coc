package com.yinmimoney.web.p2pnew.controller.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.Map;

/**
 * Created by dyf on 2019/7/30 14:18
 */
public class CityOnChatHandler2 implements HandshakeHandler {
    private static final Logger logger = LogManager.getLogger(CityOnChatHandler2.class);

    @Override
    public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {
        return true;
    }
    @OnOpen
    public void open(Session session){
      logger.info("websocket连接成功："+session.getId());
    }
    @OnClose
    public void close(Session session){
        logger.info("websocket连接断开:"+session.getId());
    }
}
