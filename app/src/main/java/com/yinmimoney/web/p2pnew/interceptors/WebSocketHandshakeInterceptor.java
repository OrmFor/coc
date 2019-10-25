package com.yinmimoney.web.p2pnew.interceptors;

import org.apache.logging.log4j.LogManager;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * Created by dyf on 2019/7/17 15:45
 */
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(WebSocketHandshakeInterceptor.class);


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info("在处理逻辑前 拦截webSocket！");
        // 解决The extension [x-webkit-deflate-frame] is not supported问题
        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        logger.info("在处理逻辑后 拦截webSocket！");
        super.afterHandshake(request, response, wsHandler, exception);
    }
}
