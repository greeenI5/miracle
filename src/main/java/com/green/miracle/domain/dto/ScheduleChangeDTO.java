package com.green.miracle.domain.dto;

import java.time.LocalDate;

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
public class ScheduleChangeDTO {

	private LocalDate startAt; //시작일
	private LocalDate finishAt; //종료일
	private String schTitle; //일정제목
	@Builder.Default
	private String schContent ="" ; //일정내용
	
	public ScheduleEntity toEntity() {
		return ScheduleEntity.builder()
				.startAt(startAt)
				.finishAt(finishAt)
				.schTitle(schTitle)
				.schContent(schContent)
				.build();
	}
}
