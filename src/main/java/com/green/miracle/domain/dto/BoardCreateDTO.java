package com.green.miracle.domain.dto;

import com.green.miracle.domain.entity.EmployeeEntity;

public class BoardCreateDTO {
	private long boardNo; // 글번호 (pk)
	private EmployeeEntity employee; //사원번호 (fk)
	private String boardTitle; //글제목
	private String boardContent; //글내용
	private long boardComment; //댓글수
}
