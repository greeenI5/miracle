package com.green.miracle.domain.dto;

import java.util.Set;

import com.green.miracle.domain.entity.Position;
import com.green.miracle.domain.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHrUpdate {
	
	private long empNo; // 사원번호
    private String name; // 사원명
    private String phone; // 핸드폰번호
    private Position position; // 직급
    private Set<Role> roles; // 역할들
    private long depCode; // 부서코드
	
}
