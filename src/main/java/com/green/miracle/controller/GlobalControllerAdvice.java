package com.green.miracle.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.MainService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
	
	private final EmployeeEntityRepository employeeRep;
	private final MainService service;
	
	@ModelAttribute
	public void addAttributes(HttpSession session, Model model, @AuthenticationPrincipal CustomUserDetails user) {
		
		if(user != null) {
			EmployeeEntity employee = employeeRep.findByEmail(user.getEmail()).orElseThrow();
			model.addAttribute("emp", employee);
			
			service.sessionTime(model, session);
		}
		
	}

}
