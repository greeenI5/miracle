package com.green.miracle.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class NoticeUpdateDTO {
	private String noticeTitle; // 공지사항 제목
	private String noticeContent; // 공지사항 내용
}
