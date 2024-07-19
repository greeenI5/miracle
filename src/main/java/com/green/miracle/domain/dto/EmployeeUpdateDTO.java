package com.green.miracle.domain.dto;

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
public class EmployeeUpdateDTO {
	private String name; //사원명
	private String email; //이메일
	private String password; //비밀번호
	private String phone; //핸드폰번호
}
