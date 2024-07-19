package com.green.miracle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.green.miracle.domain.dto.ChatBotQuestionDTO;
import com.green.miracle.domain.entity.ChatBotEntity;
import com.green.miracle.domain.repository.ChatBotRepository;
import com.green.miracle.service.ChatBotService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBotServiceProcess implements ChatBotService{

	  private final ChatBotRepository chatBotRepository;
	
	  @Override
	    public List<ChatBotQuestionDTO> getAllChatBots() {
	        return chatBotRepository.findAll().stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }

	    private ChatBotQuestionDTO convertToDTO(ChatBotEntity entity) {
	        return new ChatBotQuestionDTO(
	                entity.getQNo(),
	                entity.getQuestion(),
	                entity.getAnswer()
	        );
	    }
	}