package com.green.miracle.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.DateRequestDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.repository.BoardEntityRepository;
import com.green.miracle.domain.repository.DepartmentEntityRepository;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.domain.repository.NoticeEntityRepository;
import com.green.miracle.domain.repository.ScheduleEntityRepository;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.MainService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceProcess implements MainService{
	
	private final EmployeeEntityRepository employeeRep;
	private final NoticeEntityRepository noticeRep;
	private final BoardEntityRepository boardRep;
	private final ScheduleEntityRepository scheduleRep;
	private final DepartmentEntityRepository departmentRep;
	
	
	@Override
	public void findAllProcess(Model model, CustomUserDetails user) {
		EmployeeEntity employee = employeeRep.findByEmail(user.getEmail()).orElseThrow();
		
		//model.addAttribute("emp", employee); //GlobalControllerAdvice에 추가
		model.addAttribute("dep", departmentRep.findByDepCode(employee.getDepartment().getDepCode()));
		
		model.addAttribute("notices", noticeRep.findAll());
		model.addAttribute("boards", boardRep.findAll());

	}

	@Override
	public void scheduleProcess(Model model, LocalDate cilckDate, CustomUserDetails user) {
		EmployeeEntity employee = employeeRep.findByEmail(user.getEmail()).orElseThrow();
		model.addAttribute("schedules", scheduleRep.findByEmployeeAndStartAt(employee, cilckDate));
	}
	
	
	@Override
	public List<DateRequestDTO> scheduleProcess2(LocalDate clickDate, CustomUserDetails user) {
	    EmployeeEntity employee = employeeRep.findByEmail(user.getEmail()).orElseThrow();
	    return scheduleRep.findByEmployeeAndStartAt(employee, clickDate)
	        .stream()
	        .map(schedule -> new DateRequestDTO(schedule.getSchTitle()))
	        .collect(Collectors.toList());
	}
	

	@Override
	public void sessionTime(Model model, HttpSession session) {
		Instant creationTime = Instant.ofEpochMilli(session.getCreationTime()); //세션생성시간 (Instant 객체로)
		Instant now = Instant.now(); //현재시간
		Duration duration = Duration.between(creationTime, now);
		
		long sessionTimeout = session.getMaxInactiveInterval(); //세션 타임아웃 시간 (초단위)
		long elapsedSeconds = duration.getSeconds(); //경과시간 (초단위)
		long remainingTime = Math.max(sessionTimeout-elapsedSeconds, 0);
		
		// 남은 시간을 시:분:초 형식으로 변환
        long hours = remainingTime / 3600;
        long minutes = (remainingTime % 3600) / 60;
        long seconds = remainingTime % 60;

        String remainingTimeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        model.addAttribute("remainingTime", remainingTimeFormatted);
		
	}

	
}
