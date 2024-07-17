package com.green.miracle.domain.entity;

import java.time.LocalDateTime;

public class ChatLogsEntity {
	private long logNo; //로그번호 (pk)
	private long no; //사원번호 (fk)
	private LocalDateTime logTime; //
	private String userText; //
	private String botResponse;// 챗봇이 응답한 멧

}
