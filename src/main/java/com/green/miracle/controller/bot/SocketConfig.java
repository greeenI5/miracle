package com.green.miracle.controller.bot;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		//var socket=new SockJS("/ws-i5-bot")
		registry.addEndpoint("/ws-i5-bot").withSockJS();
		// "/ws" 엔드포인트를 통해 SockJS를 사용하여 웹소켓 연결을 설정합니다.
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/bot");
        // 클라이언트가 서버로 메시지를 보낼 때 "/bot" 접두사를 사용하도록 설정합니다.
        registry.enableSimpleBroker("/topic");
        // 서버가 클라이언트로 메시지를 보낼 때 "/topic" 접두사를 사용하도록 설정합니다.
	}


}
