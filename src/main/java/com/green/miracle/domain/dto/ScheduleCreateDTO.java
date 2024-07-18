package com.green.miracle.domain.dto;

import java.time.LocalDate;

import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.ScheduleEntity;

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
public class ScheduleCreateDTO {
	
	//필드 이름은 form 태그 내부의 입력 요소(input, select, textarea...)들의 name과 일치해야 함
	private EmployeeEntity employee; // 사원번호 (FK)
	private LocalDate startAt;
	private LocalDate finishAt;
	private String schTitle;
	private String schContent;
	
	public ScheduleEntity toEntity() {
		return ScheduleEntity.builder()
				.employee(employee)
				.schTitle(schTitle)
				.schContent(schContent)
				.startAt(startAt)
				.finishAt(finishAt)
				.build();
	}

	
	
	
}
