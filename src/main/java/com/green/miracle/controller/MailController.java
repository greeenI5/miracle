package com.green.miracle.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.MailService;


@Controller
@RequiredArgsConstructor
public class MailController {
	
	private final MailService service;
	
	@GetMapping("/oauth2/code")
	public String redirectUri(@RequestParam("code") String code, @RequestParam("state") String state, Model model, @AuthenticationPrincipal CustomUserDetails user) throws Exception {
		int mailId = 0;
		if (state.contains("-")) {
            String[] parts = state.split("-");
            state = parts[0];
            mailId = Integer.parseInt(parts[1]);
        };
		
		if(state.equals("mail.read")) {
			service.mailRead(code, model, user);
			return "views/mail/mail-list";
		}else if(state.equals("mail.detail")) {
			service.mailDetail(code, model, user, mailId);
			return "views/mail/mail-detail";
		}
		
		return "views/mail/mail-list";
	}
	
	@PostMapping("/userinfo")
	@ResponseBody
	public Map<String, Object> handleUserInfo(@RequestBody Map<String, Object> userInfo) {
        // 사용자 정보를 처리하는 로직 (예: 사용자 세션 생성, 데이터베이스 저장 등)
        System.out.println("Received user info: " + userInfo);
        
        // 사용자 정보 처리 후 응답 반환
        return Map.of("status", "success", "message", "User info processed successfully");
    }

}
