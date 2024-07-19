package com.green.miracle.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.miracle.service.MainService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MainService service;
	
	@Value("${server.servlet.session.timeout}")
	private int sessionTimeout;  // 단위: 분
	
	//사원 정보
	@GetMapping("/")
	@PreAuthorize("isAuthenticated()")
	public String list(Model model, HttpSession session) {
		long currentTime = System.currentTimeMillis(); // 현재 시간 (밀리초)
        long lastAccessedTime = session.getLastAccessedTime(); // 세션의 마지막 접근 시간 (밀리초)
        long timeElapsed = currentTime - lastAccessedTime; // 마지막 접근 이후 경과 시간 (밀리초)
        long remainingTime = (sessionTimeout * 60 * 1000L) - timeElapsed; // 남은 시간 계산 (밀리초)
        model.addAttribute("remainingTime", remainingTime); // 남은 시간을 모델에 추가하여 뷰에 전달
        
		service.findAllProcess(model);
		return "index";
	}
	

	//공연일정
	//private final 서비스
	//@GetMapping
	//public void performance(Model model) {
	//}
	
	
	//개인일정
}
