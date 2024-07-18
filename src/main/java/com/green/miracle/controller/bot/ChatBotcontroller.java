package com.green.miracle.controller.bot;



import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.green.miracle.domain.dto.ChatBotQuestionDTO;
import com.green.miracle.domain.entity.ChatBotEntity;
import com.green.miracle.service.ChatBotService;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class ChatBotcontroller {
	
	private final ChatBotService chatBotService;

	@MessageMapping("/hello") // '/bot/question' //클라이언트에서 /app/hello 경로로 보낸 메시지를 처리
	@SendTo("/topic/messages") //처리 결과를 "/topic/messages" 경로로 구독하고 있는 모든 클라이언트에게 전송
	   public ChatBotEntity chatBotQuestiondto(ChatBotQuestionDTO question) throws Exception {
		// 클라이언트가 보낸 질문을 서비스 계층으로 전달하여 답변을 가져옵니다.
        return chatBotService.getAnswerByQuestion(question.getQuestion());
    }
}
