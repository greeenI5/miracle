package com.green.miracle.domain.api;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MailController {
	
	private final MailServiceProcess service;
	private final OAuth2AuthorizedClientService authorizedClientService;
	
	@GetMapping("/mails")
	public String list(Model model) {
		
		// 현재 인증된 사용자의 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증 타입을 확인하여 처리
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
            
            // OAuth2AuthorizedClientService를 통해 인증된 클라이언트 정보를 가져옴
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                oauth2Token.getAuthorizedClientRegistrationId(),
                oauth2Token.getName()
            );

            String accessToken = client.getAccessToken().getTokenValue();
            String emails = service.getEmails(accessToken);
            model.addAttribute("emails", emails);
            
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
        	//OAuth2 인증이 안 된 상태
            //return "redirect:/login/oauth2/code/naver";
            
        } else {
            throw new IllegalStateException("예상치 못한 인증 타입: " + authentication.getClass());
        }

		return "views/mail/mail-list";
	}
	
	@GetMapping("/login/mails")
	public String getMethodName() {
		return "views/mail/mail-login";
	}

}
