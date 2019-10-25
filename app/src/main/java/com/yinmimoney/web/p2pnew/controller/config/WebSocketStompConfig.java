package com.yinmimoney.web.p2pnew.controller.config;

import com.yinmimoney.web.p2pnew.controller.handler.CityOnChatHandler;
import com.yinmimoney.web.p2pnew.controller.handler.CityOnChatHandler2;
import com.yinmimoney.web.p2pnew.interceptors.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.security.Principal;

/**
 * Created by dyf on 2019/7/17 16:00
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {
    /**
     * 将 "/stomp" 注册为一个 STOMP 端点。这个路径与之前发送和接收消息的目的地路径有所
     * 不同。这是一个端点，客户端在订阅或发布消息到目的地路径前，要连接到该端点。
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").setAllowedOrigins("*")
        .addInterceptors(new WebSocketHandshakeInterceptor())
        ;
    }


    /**
     * 如果不重载它的话，将会自动配置一个简单的内存消息代理，用它来处理以"/topic"为前缀的消息
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app", "/foo");
        registry.setUserDestinationPrefix("/user");
    }

}
