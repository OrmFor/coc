package com.yinmimoney.web.p2pnew.controller.config;

import com.yinmimoney.web.p2pnew.controller.handler.CityOnChatHandler;
import com.yinmimoney.web.p2pnew.interceptors.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Created by dyf on 2019/7/17 15:48
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    /**
     *
     * @param registry 该对象可以调用addHandler()来注册信息处理器。
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(cityOnChatHandler(),"/cityOnChat/*")
                .addInterceptors(webSocketHandshakeInterceptor()) //声明拦截器
                .setAllowedOrigins("*"); //声明允许访问的主机列表

        //声明启用SockJS连接，如果前端还用 new WebSocket(url); 会报：Error during WebSocket handshake: Unexpected response code: 200
        registry.addHandler(cityOnChatHandler(), "/marcoSockJS")
                .setAllowedOrigins("*")
                .withSockJS();
    }


    @Bean(name="cityOnChatHandler")
    public CityOnChatHandler cityOnChatHandler(){
        return new CityOnChatHandler();
    }

    @Bean
    public WebSocketHandshakeInterceptor webSocketHandshakeInterceptor(){
        return new WebSocketHandshakeInterceptor();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
