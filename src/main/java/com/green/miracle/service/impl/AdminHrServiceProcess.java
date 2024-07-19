package com.green.miracle.service.impl;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.AdminHrSaveDTO;
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
	private final PasswordEncoder pe;
	
	
	@Override
	public void findAll(Model model) {
      
    }

	@Override
	public void SaveProcess(AdminHrSaveDTO dto) {

		DepartmentEntity department = departmentRepository.findByDepCode(dto.getDepCode());
	    if (department == null) {
	        throw new IllegalArgumentException("Invalid department code: " + dto.getDepCode());
	    }

	    // dto.toEntity 메서드에 department 객체를 전달
	    repository.save(dto.toEntity(department, pe));
	}

	@Override
	public void ListProcess(int pageNumber, Model model) {
	    Sort sort = Sort.by(Direction.DESC, "empNo");
	    Pageable pageable = PageRequest.of(pageNumber, 10, sort);
	    Page<EmployeeEntity> page = repository.findAll(pageable);

	    model.addAttribute("list", page.getContent().stream()
	                                    .map(EmployeeEntity::toListDTO)
	                                    .collect(Collectors.toList()));
	    model.addAttribute("currentPage", page.getNumber());
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("totalElements", page.getTotalElements());
	}

	@Override
	public void deleteProcess(long no) {
		repository.delete(repository.findById(no).orElseThrow());
		//조회해서 존재할때만 삭제 
	}
}
