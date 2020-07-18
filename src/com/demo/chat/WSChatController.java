package com.demo.chat;
//package com.demo.ws;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//
//public class WSChatController {
//
//	@MessageMapping("/chat.sendMessage")
//	@SendTo("/topic/javainuse")
//	public ChatMessage sendMessage(@Payload ChatMessage webSocketChatMessage) {
//		return webSocketChatMessage;
//	}
//
//	@MessageMapping("/chat.newUser")
//	@SendTo("/topic/javainuse")
//	public ChatMessage newUser(@Payload ChatMessage webSocketChatMessage, SimpMessageHeaderAccessor headerAccessor) {
//		headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
//		return webSocketChatMessage;
//	}
//}
