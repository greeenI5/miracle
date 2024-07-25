package com.green.miracle.service;


import java.util.List;
import java.util.Optional;

import com.green.miracle.domain.dto.ChatBotAnswerDTO;
import com.green.miracle.domain.dto.ChatBotCategoryDTO;



public interface ChatBotService {

	

	List<ChatBotCategoryDTO> getContentByType(long type);

	List<ChatBotCategoryDTO> getScheduleByType(long type);

	List<ChatBotCategoryDTO> getNoticeByType(long type);

	Optional<ChatBotAnswerDTO> getAnswer(String keyword);
	


}
