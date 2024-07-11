package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PerformanceController {

	@GetMapping("/schedule/performance")
	public String calendar() {
		return "/views/schedule/performance/calendar.html";
	}
	
}
