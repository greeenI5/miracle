package com.green.miracle.domain.dto.api;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiveMailFolderDTO {
	
	private List<MailDTO> mails;
	private Number unreadCount;
	private String folderName;
	private int totalCount; //폴더 내의 메일 수
	//private int listCount; //현재 목록에서의 메일 개수
	//private ResponseMetaData responseMetaData; //커서가 담겨있음

}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class MailDTO {
	
	private int mailId;
	private String status; //메일상태 (Unread:안 읽은 메일, Read:읽은 메일)
	private From from;
	private String subject;
	private String sentTime; //메일 발송 시간 (example : 2021-07-16T19:20:30+09:00)
	
	// 날짜를 포맷팅하여 반환하는 메소드
	@JsonIgnore
	public String dateFormatting() {
		OffsetDateTime dateObj = OffsetDateTime.parse(sentTime);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dateObj.format(formatter);
	}
	
	
}

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
class From {
	
	private String name;
	private String email;

}
