package com.green.miracle.service;

import org.springframework.ui.Model;

import com.green.miracle.domain.dto.AdminHrSaveDTO;
import com.green.miracle.domain.dto.AdminHrUpdate;

public interface AdminHrService {
	
	void SaveProcess(AdminHrSaveDTO dto);

	void deleteProcess(long empNo);

	void ListProcess(int pageNumber, Model model);

	void UpdateProcess(long empNo, AdminHrUpdate dto);

	String getNextEmployeeNumber();

	void HrListProcess(Model model);
	
}
