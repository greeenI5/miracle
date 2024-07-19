package com.green.miracle.controller.bot;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.miracle.domain.dto.ChatBotQuestionDTO;
import com.green.miracle.service.ChatBotService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chatbots")
public class ChatBotcontroller {
	
	private final ChatBotService chatBotService;
	
	@GetMapping
    public List<ChatBotQuestionDTO> getAllChatBots() {
        return chatBotService.getAllChatBots();
    }
	
}
