package com.green.miracle.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.MailService;


@Controller
@RequiredArgsConstructor
public class MailController {
	
	private final MailService service;
	
	@GetMapping("/oauth2/code")
	public String redirectUri(@RequestParam("code") String code, Model model, @AuthenticationPrincipal CustomUserDetails user) throws Exception {
		service.mailRead(code, model, user);
		return "views/mail/mail-list";
	}
	

}
