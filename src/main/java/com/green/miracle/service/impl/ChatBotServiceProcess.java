package com.green.miracle.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.green.miracle.domain.dto.ChatBotCategoryDTO;
import com.green.miracle.domain.entity.ChatBotCategoryEntity;
import com.green.miracle.domain.repository.ChatBotRepository;
import com.green.miracle.service.ChatBotService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBotServiceProcess implements ChatBotService {
	
	private final ChatBotRepository chatBotRepository;
	

    @Override
    public List<ChatBotCategoryDTO> getContentByType(long type) {
        List<ChatBotCategoryEntity> bot = chatBotRepository.findAllByParent_cNo(type);
        return bot.stream()
                       .map(ChatBotCategoryEntity::toChatBotCategoryDTO)
                       .collect(Collectors.toList());
    }

}
