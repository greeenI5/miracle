package com.green.miracle.domain.dto;

import java.util.Set;

import com.green.miracle.domain.entity.DepartmentEntity;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.Position;
import com.green.miracle.domain.entity.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHrSaveDTO {
	
    private long empNo; // 사원번호
    private String name; // 사원명
    private String email; // 이메일
    private String password; // 비밀번호
    private String phone; // 핸드폰번호
    private Position position; // 직급
    private Set<Role> roles; // 역할들
    private long depCode; // 부서코드
    
    public EmployeeEntity toEntity(DepartmentEntity department) {
        return EmployeeEntity.builder()
                .empNo(empNo)
                .name(name)
                .email(email)
                .password(password)
                .phone(phone)
                .position(position)
                .department(department) // DepartmentEntity 객체를 직접 설정
                .roles(roles) // Set<Role>를 엔티티에 설정
                .build();
    } 
}
