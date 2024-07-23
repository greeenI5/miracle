package com.green.miracle.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BoardUpdateDTO {
	private String boardTitle; // 공지사항 제목
	private String boardContent; // 공지사항 내용
}
