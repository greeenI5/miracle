package com.green.miracle.service;

import org.springframework.ui.Model;

import com.green.miracle.domain.dto.NoticeCreateDTO;
import com.green.miracle.domain.dto.NoticeUpdateDTO;
import com.green.miracle.security.CustomUserDetails;

public interface NoticeService {
	
	void findAllProcess(Model model);
	
	void saveProcess(NoticeCreateDTO dto, CustomUserDetails user);
	
	void detailProcess(long no, Model model);
	
	void updateProcess(long no, NoticeUpdateDTO dto, CustomUserDetails user);
	
	void deleteProcess(long no);


}
