package com.green.miracle.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.ScheduleCreateDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.ScheduleEntity;
import com.green.miracle.domain.repository.ScheduleEntityRepository;
import com.green.miracle.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceProcess implements ScheduleService{
	
	private final ScheduleEntityRepository repository; //생성자 DI > 테이블에 접근한 레파지토리가 필요
	
	@Override
	public void saveProcess(ScheduleCreateDTO dto) {
		/*ScheduleEntity schedule = new ScheduleEntity();*/
		
		repository.save(dto.toEntity());
	}

	@Override
	public void findProcess(ScheduleEntity scheduleEntity, Model model) {
		List<ScheduleEntity> schedule = repository.findAll();
	    model.addAttribute("schedule", schedule);
	}

}
