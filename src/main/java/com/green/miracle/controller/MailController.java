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

}
