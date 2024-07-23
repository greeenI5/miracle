package com.green.miracle.service;


import java.util.List;

import com.green.miracle.domain.dto.ChatBotCategoryDTO;



public interface ChatBotService {

	

	List<ChatBotCategoryDTO> getContentByType(long type);

	List<ChatBotCategoryDTO> getScheduleByType(long type);

	List<ChatBotCategoryDTO> getNoticeByType(long type);



}
