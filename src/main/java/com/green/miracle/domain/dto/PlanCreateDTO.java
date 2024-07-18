package com.green.miracle.domain.dto;

import java.time.LocalDate;

import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.PerformancePlanEntity;

import jakarta.persistence.Column;
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
public class PlanCreateDTO {
	
	private long PlanNo;
	private EmployeeEntity employee;
	private String perTitle;
	private String perContent;
	private LocalDate startAt;
	private LocalDate finishAt;
	private String location;
	private String perPoster;
	private LocalDate writeAt;
	private int approval; //승인여부
	
	public PerformancePlanEntity toEntity() {
		return PerformancePlanEntity.builder()
				.planNo(PlanNo)
				.employee(employee)
				.perTitle(perTitle)
				.perContent(perContent)
				.startAt(startAt)
				.finishAt(finishAt)
				.location(location)
				.perPoster(perPoster)
				.writeAt(writeAt)
				.approval(approval)
				.build();
		
	}
}
