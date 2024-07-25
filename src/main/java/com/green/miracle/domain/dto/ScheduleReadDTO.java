package com.green.miracle.domain.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleReadDTO {
	
	//필드 이름은 form 태그 내부의 입력 요소(input, select, textarea...)들의 name과 일치해야 함
	private LocalDate startAt;
	private LocalDate finishAt;
	private String schTitle;
	private String schContent;
}
