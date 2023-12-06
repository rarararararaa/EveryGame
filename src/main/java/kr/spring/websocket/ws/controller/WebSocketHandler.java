package kr.spring.websocket.ws.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.spring.websocket.ws.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
	//객테로부터 Json 형태의 문자열을 만들어낸다.
	private final ObjectMapper mapper;
	//현재 연결된 세션들 
	private final Set<WebSocketSession> sessions = new HashSet<>();
	//chatRoomId : {session1, session2}
	private final Map<Integer, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();
	
	@Override //소캣 연결 확인
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("{} 연결됨", session.getId());
		sessions.add(session);
	}
	@Override //소캣 통신 시 메세지의 전송을 다루는 부분
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String payLoad = message.getPayload().toString();
		log.info("payLoad {}", payLoad);
		
		//페이로드 -> ChatMessageDTO로 변환
		ChatMessageDto chatMessageDto = mapper.readValue(payLoad, ChatMessageDto.class);//역직렬화
		log.info("session {}", chatMessageDto.toString());
		
		int chat_room_id = chatMessageDto.getChat_room_num();
		//메모리 상에 채팅방에 대한 세션이 없으면 만들어줌
		if(!chatRoomSessionMap.containsKey(chat_room_id))	
			chatRoomSessionMap.put(chat_room_id, new HashSet<>());
		Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chat_room_id);
		
		//message에 담긴 타입을 확인
		//이때 message에서 getType	으로 가져온 내용이 chatDTO의 열거형인 MessageType 안에 있는 ENTER과 동일한 값이면
		if(chatMessageDto.getMessageType().equals(ChatMessageDto.MessageType.ENTER)) {
			//sessions에 넘어온 session을 담고,
			chatRoomSession.add(session);
		}
		if(chatRoomSession.size() > 5) {
			removeCloseSession(chatRoomSession);
		}
		sendMessageToChatRoom(chatMessageDto, chatRoomSession);
	}
	@Override //소켓 종료
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("{} 연결 끊김", session.getId());
		sessions.remove(session);
	}
	
	//==========채팅 관련 메소드==============//
	private void removeCloseSession(Set<WebSocketSession> chatRoomSession) {
		chatRoomSession.removeIf(sess -> sessions.contains(sess));//이게 뭐고........
	}
	private void sendMessageToChatRoom(ChatMessageDto chatMessageDto, Set<WebSocketSession>  chatRoomSession) {
		chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDto));
	}
	
	public <T> void sendMessage(WebSocketSession session, T message) {
		try {
			session.sendMessage(new TextMessage(mapper.writeValueAsBytes(message)));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
}
