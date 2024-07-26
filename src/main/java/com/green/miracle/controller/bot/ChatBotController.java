package com.green.miracle.controller.bot;


import com.green.miracle.domain.dto.ChatBotAnswerDTO;
import com.green.miracle.domain.dto.ChatBotCategoryDTO;
import com.green.miracle.domain.entity.ChatBotCategoryEntity;
import com.green.miracle.service.ChatBotService;


import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatBotController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatBotService chatBotService;
    
	@MessageMapping("/hello")
	public void hello(Question dto) {
		System.out.println(">>>hello:"+dto);
		long key=dto.getKey();
		String pattern="안녕하세요! 질문을 선택해주세요";
		messagingTemplate.convertAndSend("/topic/bot/"+key,
				MessageFormat.format(pattern,dto.getName())
				);
	}
    @GetMapping("/chatbot/contact")
    public List<ChatBotCategoryDTO> getContent(@RequestParam("type") long type) {
        return chatBotService.getContentByType(type);
    }
    @GetMapping("/chatbot/schedule")
    public List<ChatBotCategoryDTO> getSchedule(@RequestParam("type") long type) {
        return chatBotService.getScheduleByType(type);
    }
    @GetMapping("/chatbot/notice")
    public List<ChatBotCategoryDTO> getNotice(@RequestParam("type") long type) {
        return chatBotService.getNoticeByType(type);
    }
    @GetMapping("/chatbot/contact/dpt")
    public List<ChatBotCategoryDTO> getContentDpt(@RequestParam("type") long type) {
        return chatBotService.getContentByType(type);
    }
    @GetMapping("/chatbot/contact/dpt/s1")
    public List<ChatBotCategoryDTO> fetchContactDptS1(@RequestParam("type") long type) {
        return chatBotService.getContentByType(type);
    }
    

    @MessageMapping("/search")
    public void question(ChatBotQuestionDTO dto) {
        System.out.println(">>>search:" + dto);
        long key = dto.getKey();
       
        String response = chatBotService.getAnswer(dto.getKeyword())
                .map(ChatBotAnswerDTO::getContent)
                .orElse("알 수 없는 질문입니다.");

        messagingTemplate.convertAndSend("/topic/bot/" + key, response);
    }
    

}


