package com.green.miracle.domain.dto;

import java.time.LocalDate;

import com.green.miracle.domain.entity.EmployeeEntity;

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
public class BoardDetailDTO {
	private long boardNo; // 글번호 (pk)
	private EmployeeEntity employee; //사원번호 (fk)
	private String boardTitle; //글제목
	private String boardContent; //글내용
	private long boardComment; //댓글수
	private LocalDate writeAt; // 작성일
}
