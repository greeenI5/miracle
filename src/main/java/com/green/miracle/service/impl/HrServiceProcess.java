package com.green.miracle.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.EmployeeDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.service.HrService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HrServiceProcess implements HrService {
	
	private final EmployeeEntityRepository repository;
	
	@Override
    public void HrListProcess(Model model) {
        // 모든 사원 목록을 조회
        List<EmployeeDTO> empDTOs = repository.findAll().stream()
                .map(EmployeeEntity::toListdto) // EmployeeEntity를 EmployeeDTO로 변환
                .collect(Collectors.toList());

        // 부서 코드(팀 이름)별로 사원들을 그룹화
        Map<Long, List<EmployeeDTO>> groupedEmployees = empDTOs.stream()
                .collect(Collectors.groupingBy(EmployeeDTO::getDepCode)); // depCode를 팀 이름으로 사용

        // 모델에 그룹화된 사원 목록을 추가
        model.addAttribute("groupedEmployees", groupedEmployees);
    }

	@Override
	public void detailProcess(long no, Model model) {
		EmployeeEntity result = repository.findById(no).orElseThrow();
		model.addAttribute("detail", result.toHrDetailDTO());
		
	}

}
