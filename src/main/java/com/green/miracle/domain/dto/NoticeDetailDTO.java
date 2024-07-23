package com.green.miracle.domain.dto;

import java.time.LocalDate;

import com.green.miracle.domain.entity.EmployeeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDetailDTO {
	private long noticeNo; // 글 번호
	private EmployeeEntity employee; //사원번호 (fk)
	private String noticeTitle; // 공지사항 제목
	private String noticeContent; // 공지사항 내용
	private LocalDate writeAt; // 작성일
}
