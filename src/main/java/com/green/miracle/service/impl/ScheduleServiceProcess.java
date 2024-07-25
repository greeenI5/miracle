package com.green.miracle.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.ScheduleReadDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.ScheduleEntity;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.domain.repository.ScheduleEntityRepository;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceProcess implements ScheduleService{
	
	private final ScheduleEntityRepository repository; //생성자 DI > 테이블에 접근한 레파지토리가 필요
	private final EmployeeEntityRepository empRepository;
	
	@Override
	public void findScheduleProcess(Model model, LocalDate cilckDate, CustomUserDetails user) {
		EmployeeEntity employee = empRepository.findByEmail(user.getEmail()).orElseThrow();
		
		List<ScheduleEntity> allSchedules = repository.findByEmployeeAndStartAt(employee, cilckDate);
		List<ScheduleEntity> filterSchedules = allSchedules.stream()
                								.filter(schedule -> schedule.getStartAt().equals(cilckDate))
                								.limit(4) // 최대 4개까지만 가져오게 함
                								.collect(Collectors.toList());
		model.addAttribute("schedules", filterSchedules);
	}
	
	@Override
	public List<ScheduleReadDTO> scheduleChangeProcess(LocalDate clickDate, CustomUserDetails user) {
		EmployeeEntity employee = empRepository.findByEmail(user.getEmail()).orElseThrow();
		return repository.findByEmployeeAndStartAt(employee, clickDate)
				.stream()
				.map(schedule -> new ScheduleReadDTO(
						schedule.getStartAt(),schedule.getFinishAt()
						,schedule.getSchTitle(),schedule.getSchContent()))
				//순서 *중요
				.collect(Collectors.toList());
	}

	
	

}
