package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PlanController {
	
	@GetMapping("/performance/plan")
	public String list() {
		return "views/plan/list";
	}
	
	@GetMapping("/performance/plan/form")
	public String write() {
		return "views/plan/write";
	}
	
	@GetMapping("/performance/plan/{plan_no}")
	public String view() {
		return "views/plan/view";
	}
	
}
