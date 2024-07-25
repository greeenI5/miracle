package com.green.miracle.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.EmployeeUpdateDTO;
import com.green.miracle.domain.entity.DepartmentEntity;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.MyPageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyPageServiceProcess implements MyPageService {

	private final EmployeeEntityRepository repository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void updateProcess(EmployeeUpdateDTO dto, CustomUserDetails user) {
	    // 이메일을 기반으로 직원 정보를 데이터베이스에서 찾아옴
	    EmployeeEntity employee = repository.findByEmail(user.getEmail())
	            .orElseThrow(() -> new RuntimeException("Employee not found"));

	    // DTO에서 넘어온 필드들로 기존 직원 정보를 업데이트
	    employee.setEmail(dto.getEmail());
	    employee.setPhone(dto.getPhone());

	    // 비밀번호가 변경된 경우에만 암호화하여 업데이트
	    if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
	        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
	        employee.setPassword(encryptedPassword);
	    }

	    // 업데이트된 직원 정보를 데이터베이스에 저장
	    System.out.println(">>>>>"+employee);
	    repository.save(employee);
	}

	
	@Override
	public void readProcess(Model model, CustomUserDetails user) {
		// 사용자의 이메일을 기반으로 EmployeeEntity를 조회합니다.
		EmployeeEntity employee = repository.findByEmail(user.getEmail())
				.orElseThrow(() -> new RuntimeException("Employee not found for email: " + user.getEmail()));

		// EmployeeEntity에서 부서 정보를 가져옵니다.
		DepartmentEntity department = employee.getDepartment();

		// 부서 정보가 null인 경우 예외를 던집니다.
		if (department == null) {
			throw new RuntimeException("Department not found for employee with email: " + user.getEmail());
		}
		
		// 부서 코드를 기반으로 실제 부서 정보를 조회하고 모델에 추가합니다.
		model.addAttribute("depName", department.getDepName());
	}

}
