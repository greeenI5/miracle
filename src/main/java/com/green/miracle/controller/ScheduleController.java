package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ScheduleController {
	
	/*
	private final ScheduleService service;//생성자 DI
	//spring 이 service객체를 생성자를 통해서 주입 시킨다.
	*/
	@GetMapping("/schedule") //url 엮기 홈에서 이동
	public String calendar() {
		return "/views/schedule/calendar.html";
	}
	/*
	@GetMapping("/schedule")
	public String schedule(Model model) { //캘린더에서 일정 출력  
		service.findAllProcess(model);
		return "";
	}
	*/
	
}
