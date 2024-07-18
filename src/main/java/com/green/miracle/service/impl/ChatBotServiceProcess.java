package com.green.miracle.service.impl;

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
	// ChatBotRepository 인터페이스를 통한 데이터베이스 접근을 담당하는 필드
	
	@Override
	public ChatBotEntity getAnswerByQuestion(String question) {
		// ChatBotRepository를 사용하여 데이터베이스에서 질문에 대한 답변을 조회
        return chatBotRepository.findAnswerByQuestion(question);
    }
	
}
