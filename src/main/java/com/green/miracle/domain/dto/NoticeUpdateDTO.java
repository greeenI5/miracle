package com.green.miracle.domain.dto;

import com.green.miracle.domain.entity.EmployeeEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class NoticeUpdateDTO {
	private EmployeeEntity employee; //사원번호 (fk)
	private String noticeTitle; // 공지사항 제목
	private String noticeContent; // 공지사항 내용
}
