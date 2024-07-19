package com.green.miracle.service;

import org.springframework.ui.Model;

import com.green.miracle.domain.dto.AdminHrSaveDTO;

public interface AdminHrService {
	
	void SaveProcess(AdminHrSaveDTO dto);
	
	void findAll(Model model);

	void deleteProcess(long no);

	void ListProcess(int pageNumber, Model model);

	

}
