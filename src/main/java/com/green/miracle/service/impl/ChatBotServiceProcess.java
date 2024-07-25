package com.green.miracle.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.green.miracle.domain.dto.ChatBotAnswerDTO;
import com.green.miracle.domain.dto.ChatBotCategoryDTO;
import com.green.miracle.domain.entity.ChatBotCategoryEntity;
import com.green.miracle.domain.repository.ChatBotAnswerEntityRepository;
import com.green.miracle.domain.repository.ChatBotRepository;
import com.green.miracle.service.ChatBotService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBotServiceProcess implements ChatBotService {
	
	private final ChatBotRepository chatBotRepository;
	
	private final ChatBotAnswerEntityRepository answerRepository;
	

    @Override
    public List<ChatBotCategoryDTO> getContentByType(long type) {
        List<ChatBotCategoryEntity> bot = chatBotRepository.findAllByParent_cNo(type);
        return bot.stream()
                       .map(ChatBotCategoryEntity::toChatBotCategoryDTO)
                       .collect(Collectors.toList());
    }


	@Override
	public List<ChatBotCategoryDTO> getScheduleByType(long type) {
        List<ChatBotCategoryEntity> bot = chatBotRepository.findAllByParent_cNo(type);
        return bot.stream()
                       .map(ChatBotCategoryEntity::toChatBotCategoryDTO)
                       .collect(Collectors.toList());
    }


	@Override
	public List<ChatBotCategoryDTO> getNoticeByType(long type) {
        List<ChatBotCategoryEntity> bot = chatBotRepository.findAllByParent_cNo(type);
        return bot.stream()
                       .map(ChatBotCategoryEntity::toChatBotCategoryDTO)
                       .collect(Collectors.toList());
	}


    @Override
    public Optional<ChatBotAnswerDTO> getAnswer(String keyword) {
        return answerRepository.findByKeyword(keyword)
                .map(answer -> new ChatBotAnswerDTO(answer.getAnswerNo(), answer.getKeyword(), answer.getContent()));
    }

}
