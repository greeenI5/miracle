package com.green.miracle.domain.dto;

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
public class ApprovalChangeDTO {
	
	private int approval;
	
	public PerformancePlanEntity toChangeEntity() {
		return PerformancePlanEntity.builder()
				.approval(approval)
				.build();
	}
}
