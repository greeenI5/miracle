package com.green.miracle.domain.dto;

import java.util.Set;

import com.green.miracle.domain.entity.DepartmentEntity;
import com.green.miracle.domain.entity.Position;
import com.green.miracle.domain.entity.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminHrListDTO {
    
	private long empNo; // 사원번호
    private String name; // 사원명
    private Set<Role> roles; // 역할들
    private Position position; // 직급
    private long depCode; // 부서코드
    private String phone; // 핸드폰번호

    // 생성자, 게터 등 필요한 추가 메서드들을 포함할 수 있습니다.
}


