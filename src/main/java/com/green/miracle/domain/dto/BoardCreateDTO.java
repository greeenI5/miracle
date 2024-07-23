package com.green.miracle.domain.dto;

import java.time.LocalDate;

import com.green.miracle.domain.entity.BoardEntity;
import com.green.miracle.domain.entity.EmployeeEntity;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class BoardCreateDTO {
	private long boardNo; // 글번호 (pk)
	private EmployeeEntity employee; //사원번호 (fk)
	private String boardTitle; //글제목
	private String boardContent; //글내용
	private LocalDate writeAt; // 작성일
	
	public BoardEntity toEntity() {
		return BoardEntity.builder()
				.boardNo(boardNo)
				.employee(employee)
				.boardTitle(boardTitle)
				.boardContent(boardContent)
				.writeAt(writeAt)
				.build();
	}
}
