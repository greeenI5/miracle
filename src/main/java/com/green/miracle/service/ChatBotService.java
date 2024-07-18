package com.green.miracle.service;

import com.green.miracle.domain.dto.ChatBotQuestionDTO;
import com.green.miracle.domain.entity.ChatBotEntity;

public interface ChatBotService {

	ChatBotEntity getAnswerByQuestion(String question);
	

}
