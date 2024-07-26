package com.green.miracle.domain.dto;

import java.util.Set;

import com.green.miracle.domain.entity.Position;
import com.green.miracle.domain.entity.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class EmployeeDTO {
	
	private long empNo;
	private Set<Role> roles; // 역할들
    private String name; // 사원명
    private Position position; // 직급
    private long depCode; // 부서코드
	
}
