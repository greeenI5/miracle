package com.green.miracle.domain.dto;

import com.green.miracle.domain.entity.Position;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HrDetailDTO {
	private long empNo;
    private String name; // 사원명
    private String email; // 이메일
    private String phone; // 핸드폰번호
    private Position position; // 직급
    private long depCode; // 부서코드
}
