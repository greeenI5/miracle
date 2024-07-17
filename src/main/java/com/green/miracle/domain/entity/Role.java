package com.green.miracle.domain.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
	//Enum-name="ADMIN|EMP", ordinal=0~
	EMP("사원")
	,ADMIN("관리자")
	;
	
	private final String roleName;
	
	public final String roleName() {
		return roleName;
	}
}

