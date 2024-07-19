package com.green.miracle.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final RequestCache requestCache = new HttpSessionRequestCache();
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private static final String DEFAULT_SUCCESS_URL = "/";
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		clearAuthenticationAttributes(request); //인증 실패 또는 인증과 관련된 메세지와 상태정보가 이후 요청에 영향을 미치지 않도록 제거
		String targetUrl = getDefaultTargetUrl();
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		System.out.println("savedRequest: "+savedRequest);
		
		String prevPage = (String) request.getSession().getAttribute("prevPage");
		System.out.println("PrevPage: "+prevPage);
		
		if(savedRequest != null && !savedRequest.getRedirectUrl().contains("login")) {
			targetUrl = savedRequest.getRedirectUrl().split("[?]")[0];//split ?를 기준으로 배열 0번째니까 앞쪽것만
			System.out.println("targetUrl: "+targetUrl);
		}else if(prevPage != null) {
			targetUrl = prevPage;
			request.getSession().removeAttribute("prevPage");
		}
		
		redirectStrategy.sendRedirect(request, response, targetUrl); //성공하면 인덱스로 넘어감
		//redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
	}

}
