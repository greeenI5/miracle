package com.green.miracle.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.miracle.domain.dto.ChatBotQuestionDTO;
import com.green.miracle.domain.entity.ChatBotEntity;

public interface ChatBotRepository extends JpaRepository<ChatBotEntity, Long>{

	
	// 질문에 대한 답변을 반환하는 메서드를 선언합니다.
	ChatBotEntity findAnswerByQuestion(String answer);

}
