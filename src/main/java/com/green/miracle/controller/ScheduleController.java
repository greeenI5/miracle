package com.green.miracle.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.miracle.domain.entity.ScheduleEntity;
import com.green.miracle.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

//@RequestMapping 애노테이션은 이 클래스의 기본 URL 경로를 정의합니다.
@RequiredArgsConstructor
@Controller
public class ScheduleController {
	
	private final ScheduleService service;
	
	@GetMapping("/schedule")
    public String getCalendar(Model model) {



        return "/views/schedule/calendar";
    }
	
	
}
