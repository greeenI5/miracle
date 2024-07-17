package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class ApprovalController {
	
	@GetMapping("approval/plan")
	public String list() {
		return "views/approval/list";
	}
	
	@GetMapping("approval/plan/{plan_no}")
	public String write() {
		return "views/approval/view";
	}
	
}
