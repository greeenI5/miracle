package com.green.miracle.domain.dto.api;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class MailDetailDTO {
	
	private Mail mail;

}

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
class Mail{
	From from;
	String subject;
	String body;
	String sentTime;
	
	// 날짜를 포맷팅하여 반환하는 메소드
	@JsonIgnore
	public String dateFormatting() {
		OffsetDateTime dateObj = OffsetDateTime.parse(sentTime);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dateObj.format(formatter);
	}
}