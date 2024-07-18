package com.green.miracle.domain.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.green.miracle.domain.entity.EmployeeEntity;

import lombok.Setter;

@Setter
public class EmployeeSaveDTO {

	private String email;
	private String password;
	private String name;
	private String role;
	
	public EmployeeEntity toEntity(PasswordEncoder pe) {
		return EmployeeEntity.builder()
				.email(email)
				.password(pe.encode(password))
				.name(name)
				.build() //roles 콜렉션 객체도 생성됨
				.addRoleByRange(role); //편의 메서드 만들었을때
	}
}
