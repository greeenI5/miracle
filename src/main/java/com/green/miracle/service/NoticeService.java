package com.green.miracle.service;

import org.springframework.ui.Model;

import com.green.miracle.domain.dto.NoticeCreateDTO;
import com.green.miracle.domain.dto.NoticeDetailDTO;

public interface NoticeService {
	
	void findAllProcess(Model model);
	
	void saveProcess(NoticeCreateDTO dto);
	
	void detailProcess(long no, Model model);
	
	void updateProcess(long no, NoticeDetailDTO dto);
	
	void deleteProcess(long no);
}
