package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.green.miracle.domain.dto.EmployeeUpdateDTO;
import com.green.miracle.service.impl.MyPageServiceProcess;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MyPageController {
	
	private final MyPageServiceProcess service;
	
	@GetMapping("/mypage")
	public String list() {
		return "views/user/mypage";
	}
	
	@PutMapping("/mypage")
	public String update(EmployeeUpdateDTO dto) {
		service.updateProcess(dto);		
		return "redirect:/mypage";
	}
	
}
