package com.green.miracle.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.service.MailService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.green.miracle.domain.api.OpenApiUtil;
import com.green.miracle.domain.dto.api.NaverTokenDTO;
import com.green.miracle.domain.dto.api.ReceiveMailFolderDTO;
import com.green.miracle.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceProcess implements MailService{
	
	private final OpenApiUtil openApiUtil;
	
	@Value("${naver.client.id}")
	String clientId;
	
	@Value("${naver.client.secret}")
	String clientSecret;
	
	@Value("${naver.client.domain}")
	String domainId;
	
	@Override
	public void mailRead(String code, Model model, CustomUserDetails user) throws Exception {
		//접근하기 위한 토큰 생성
		String responseResult = getAccessToken(code);
		
		NaverTokenDTO result = openApiUtil.objectMapper(responseResult, new TypeReference<NaverTokenDTO>() {});
		String accessToken = result.getAccess_token();
		String method="GET";
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer "+accessToken);
		
		String userId = "gr.16380@greenmiracle.by-works.com"; //이걸 지금 인증된 정보에서 가져오기!
		String encodedUserId = URLEncoder.encode(userId, StandardCharsets.UTF_8.toString());
		
		/*메일함 목록 조회
		String apiUrl = "https://www.worksapis.com/v1.0/users/";
		StringBuilder urlBuilder= new StringBuilder(apiUrl);
		urlBuilder.append(encodedUserId); urlBuilder.append("/mail/mailfolders");
		apiUrl = urlBuilder.toString();
		String mailFoldersResponseResult = openApiUtil.request(apiUrl, headers, method, null);
		System.out.println("mailFoldersResponseResult: "+mailFoldersResponseResult);
		*/
		
		//받은메일함 내 메일 목록 조회
		String apiUrl = "https://www.worksapis.com/v1.0/users/";
		StringBuilder urlBuilder= new StringBuilder(apiUrl);
		urlBuilder.append(encodedUserId); urlBuilder.append("/mail/mailfolders/0/children");
		apiUrl = urlBuilder.toString();
		String receiveMailsResponseResult = openApiUtil.request(apiUrl, headers, method, null);
		System.out.println("receiveMailsResponseResult: "+receiveMailsResponseResult);
		
		//JSON 문자열 -> 자바의 객체로 변환
		ReceiveMailFolderDTO receiveMailFolder = openApiUtil.objectMapper(receiveMailsResponseResult, new TypeReference<ReceiveMailFolderDTO>() {});
		model.addAttribute("receiveMailFolder", receiveMailFolder);
		
	}
	
	//토큰 얻어오는 메서드
	private String getAccessToken(String code) {
		
		String apiUrl = "https://auth.worksmobile.com/oauth2/v2.0/token";
		StringBuilder urlBuilder= new StringBuilder(apiUrl);
		urlBuilder.append("?code="); urlBuilder.append(code);
		urlBuilder.append("&grant_type=authorization_code");
		urlBuilder.append("&client_id="); urlBuilder.append(clientId);
		urlBuilder.append("&client_secret="); urlBuilder.append(clientSecret);
		apiUrl = urlBuilder.toString();
		
		String method = "POST";
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		
		return openApiUtil.request(apiUrl, headers, method, null);
		
	}

}
