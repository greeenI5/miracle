package com.green.miracle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.green.miracle.domain.dto.ScheduleCreateDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.ScheduleEntity;
import com.green.miracle.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping 애노테이션은 이 클래스의 기본 URL 경로를 정의합니다.
@RequiredArgsConstructor
@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	private final ScheduleService service;
	/*
	@GetMapping("/")
	public String getSchedule(ScheduleEntity scheduleEntity, Model model) { //캘린더에서 일정 출력  
		service.findProcess(scheduleEntity,model);
		return "/views/schedule/calendar";
	}*/
	
	@PostMapping("/save")
    public ResponseEntity<String> saveSchedule(@RequestBody ScheduleCreateDTO dto) {
        try {
            service.saveProcess(dto);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }
	
}
