package com.green.miracle.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.ScheduleCreateDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.ScheduleEntity;

public interface ScheduleService {
	

	void saveProcess(ScheduleCreateDTO dto);


	void findProcess(ScheduleEntity scheduleEntity, Model model);
}
