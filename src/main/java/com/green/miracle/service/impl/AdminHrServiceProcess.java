package com.green.miracle.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.AdminHrSaveDTO;
import com.green.miracle.domain.dto.AdminHrUpdate;
import com.green.miracle.domain.dto.EmployeeDTO;
import com.green.miracle.domain.entity.DepartmentEntity;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.repository.DepartmentEntityRepository;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.service.AdminHrService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminHrServiceProcess implements AdminHrService {
	
	private final EmployeeEntityRepository repository;
	private final DepartmentEntityRepository departmentRepository;
	private final PasswordEncoder passwordEncoder;
	
	
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
	
	//페이지네이션과 리스트
		@Override
		public void ListProcess(int pageNumber, Model model) {
		    Sort sort = Sort.by(Direction.DESC, "empNo");
		    Pageable pageable = PageRequest.of(pageNumber, 15, sort);
		    Page<EmployeeEntity> page = repository.findAll(pageable);

		    model.addAttribute("list", page.getContent().stream()
		                                    .map(EmployeeEntity::toListDTO)
		                                    .collect(Collectors.toList()));
		    model.addAttribute("currentPage", page.getNumber()); // 페이지 번호는 1부터 시작하도록 표시
		    model.addAttribute("totalPages", page.getTotalPages());
		    model.addAttribute("totalElements", page.getTotalElements());
		}
	
	@Override
	public void SaveProcess(AdminHrSaveDTO dto) {

		DepartmentEntity department = departmentRepository.findByDepCode(dto.getDepCode());
	    if (department == null) {
	        throw new IllegalArgumentException("Invalid department code: " + dto.getDepCode());
	    }

	    // dto.toEntity 메서드에 department 객체를 전달
	    repository.save(dto.toEntity(department, passwordEncoder));
	}
		
	//자동으로 사원번호의 다음값을 가지고 옴
	@Override
	public String getNextEmployeeNumber() {
	    Integer maxEmpNo = repository.findMaxEmployeeNumber();

	    // 초기값 설정
	    if (maxEmpNo == null) {
	        maxEmpNo = 1089; // 초기값 설정 (예: 1089)
	    }

	    return String.valueOf(maxEmpNo + 1);
	}

	@Override
	public void deleteProcess(long empNo) {
	    repository.delete(repository.findById(empNo).orElseThrow(() -> new IllegalArgumentException("Employee not found")));
	}

	@Override
	@Transactional
	public void UpdateProcess(long empNo, AdminHrUpdate dto) {
		DepartmentEntity department = departmentRepository.findByDepCode(dto.getDepCode());
        if (department == null) {
            throw new IllegalArgumentException("Invalid department code: " + dto.getDepCode());
        }
		
		repository.findById(empNo).get().update(dto, department);
		
	}

	
}
