package com.green.miracle.domain.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.green.miracle.domain.entity.EmployeeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateDTO {
	private String name; //사원명
	private String email; //이메일
	private String password; //비밀번호
	private String phone; //핸드폰
	
	public EmployeeEntity toEntity(PasswordEncoder pe) {
		return EmployeeEntity.builder()
				.email(email)
				.password(pe.encode(password))
				.phone(phone)
				.build();
	}
	
}
