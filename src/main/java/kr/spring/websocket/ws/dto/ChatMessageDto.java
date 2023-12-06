package kr.spring.websocket.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessageDto {
	public enum MessageType{//여러개의 상수 값
		ENTER, TALK
	}
	private MessageType messageType; //메시지 타입
	private int chat_room_num; //채팅방 번호
	private int mem_num; //채팅을 보낸 사람
	private String message; //메시지
}
