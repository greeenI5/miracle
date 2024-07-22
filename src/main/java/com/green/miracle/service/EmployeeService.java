package com.green.miracle.service;

import com.green.miracle.domain.dto.EmployeeSaveDTO;
import com.green.miracle.domain.entity.EmployeeEntity;

public interface EmployeeService {

	void loginProcess(EmployeeSaveDTO dto);

	EmployeeEntity findById(Long empNo);
	

}
