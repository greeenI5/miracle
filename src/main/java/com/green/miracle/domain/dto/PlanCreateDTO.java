package com.green.miracle.domain.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.PerType;
import com.green.miracle.domain.entity.PerformancePlanEntity;

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
	
	private EmployeeEntity employee;
	private String perTitle;
	private PerType perType;
	private String perContent;
	private LocalDate startAt;
	private LocalDate finishAt;
	private String location;
	private String perPoster;
	private LocalDate writeAt;
	
	public PerformancePlanEntity toEntity() {
		return PerformancePlanEntity.builder()
				//.perPoster(perPoster != null ? perPoster.getOriginalFilename() : null)
				.perPoster(perPoster)
				.employee(employee)
				.perTitle(perTitle)
				.perType(perType)
				.perContent(perContent)
				.startAt(startAt)
				.finishAt(finishAt)
				.location(location)
				.writeAt(writeAt)
				.build();
	}
}
