package com.green.miracle.controller.hr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminHrController {
	
	@GetMapping("/admin/hr")
	public String list() {
		return "views/admin/hrm";
	}
	
	@PostMapping("/admin/hr/mgm")
	public String detail() {
		return "views/admin/mgm";
	}
	
}
