package com.green.miracle.domain.dto;

import java.time.LocalDate;

import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.NoticeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeCreateDTO {
	
	private long noticeNo; // 글 번호
	private EmployeeEntity employee; //사원번호 (fk)
	private String noticeTitle; // 공지사항 제목
	private String noticeContent; // 공지사항 내용
	private LocalDate writeAt; // 작성일
	
	
	public NoticeEntity toEntity() {
		return NoticeEntity.builder()
					.noticeNo(noticeNo)
					.employee(employee)
					.noticeTitle(noticeTitle)
					.noticeContent(noticeContent)
					.writeAt(writeAt)
					.build();
	}
}
