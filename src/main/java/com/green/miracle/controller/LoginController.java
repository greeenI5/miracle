package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.miracle.domain.dto.EmployeeSaveDTO;
import com.green.miracle.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final EmployeeService service;

	@GetMapping("/login")
	public String login() {
		return "views/user/login";
	}
	
	@PostMapping("/login")
	public String login(EmployeeSaveDTO dto) {
		service.loginProcess(dto);
		return "redirect:/";
	}
	
	
}
