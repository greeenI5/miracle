package com.green.miracle.domain.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceProcess {
	
	@Value("${naver.api.url}")
    private String apiUrl; // Naver API URL을 설정 파일에서 가져옴
    private final RestTemplate restTemplate;

    // Access Token을 사용하여 OAuth2 사용자의 이메일 가져오기
    public String getEmails(String accessToken) {
        String url = apiUrl + "/users/me/mail";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken); // Authorization 헤더에 Bearer 토큰 추가
        HttpEntity<String> entity = new HttpEntity<>(headers); // 헤더를 포함한 HttpEntity 생성

        // Naver API에 GET 요청을 보내고 응답 받기
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
