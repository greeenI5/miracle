package com.green.miracle.controller.hr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.miracle.service.AdminHrService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminHrController {
	
	private final AdminHrService service;
	
	@GetMapping("/admin/hr")
	public String list(Model model) {
		service.findAll(model);
		return "views/admin/hrm";
	}
	
	@PostMapping("/admin/hr/mgm")
	public String detail() {
		return "views/admin/mgm";
	}
	
}
