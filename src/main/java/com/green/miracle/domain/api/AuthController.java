package com.green.miracle.domain.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AuthController {
	
	@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String clientId;
	
	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;
	
    private String redirectUri = "http://localhost:8080/login/oauth2/code/naver";
    private String scope = "openid email mail";

    @GetMapping("/auth")
    public void authorize(HttpServletResponse response) throws IOException {
        String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString());
        String encodedScope = URLEncoder.encode(scope, StandardCharsets.UTF_8.toString());
        
        String authUrl = String.format(
                "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&scope=%s",
                clientId, encodedRedirectUri, encodedScope);
        response.sendRedirect(authUrl);
    }

    @GetMapping("/auth/callback")
    public ResponseEntity<String> callback(@RequestParam("code") String code, @RequestParam("state") String state) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = String.format(
                "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s",
                clientId, clientSecret, code, URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString()));
        ResponseEntity<String> response = restTemplate.getForEntity(tokenUrl, String.class);
        return response;
    }

}
