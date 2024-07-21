package com.green.miracle.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.MainService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MainService service;
	
	@GetMapping("/")
	public String list(Model model, @AuthenticationPrincipal CustomUserDetails user) {
		//service.sessionTime(model, session); //GlobalControllerAdvice에 추가
        service.findAllProcess(model, user);
		return "index";
	}

}
