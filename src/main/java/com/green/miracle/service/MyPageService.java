package com.green.miracle.service;

import org.springframework.ui.Model;

import com.green.miracle.domain.dto.EmployeeUpdateDTO;
import com.green.miracle.security.CustomUserDetails;

public interface MyPageService {

	void readProcess(Model model, CustomUserDetails user);
	
	void updateProcess(EmployeeUpdateDTO dto, CustomUserDetails user);
	
}
