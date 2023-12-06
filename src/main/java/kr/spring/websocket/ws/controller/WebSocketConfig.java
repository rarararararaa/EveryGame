package kr.spring.websocket.ws.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import kr.spring.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
//교차되는 출처 자원들을 공유 Configuration - Bean 수동 등록(Component 상속) Component - Bean 자동등록
@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	private final WebSocketHandler webSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// ws/chat 경로를 통해 들어오는 웹 소켓 통신 요청에 대한 처리를 위한 Handler 추가
		// setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌
		registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
	}
	
}
