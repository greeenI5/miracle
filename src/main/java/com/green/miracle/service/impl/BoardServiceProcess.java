package com.green.miracle.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.BoardCreateDTO;
import com.green.miracle.domain.dto.BoardDetailDTO;
import com.green.miracle.domain.entity.BoardEntity;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.repository.BoardEntityRepository;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceProcess implements BoardService {
	
	private final BoardEntityRepository repository;
	private final EmployeeEntityRepository empRepository;
	
	@Override
	public void findAllProcess(Model model) {
		model.addAttribute("list", repository.findAll());
	}

	@Override
	public void saveProcess(BoardCreateDTO dto, CustomUserDetails user) {
		dto.setEmployee(empRepository.findByEmail(user.getEmail()).orElseThrow());
		repository.save(dto.toEntity());
	}

	@Override
	public void detailProcess(long no, Model model) {
		BoardEntity result = repository.findById(no).orElseThrow();
		model.addAttribute("detail", result.toBoardDetailDTO());
	}

	@Override
	@Transactional
	public void updateProcess(long no, BoardDetailDTO dto) {
		repository.findById(no).orElseThrow().update(dto);
	}

	@Override
	public void deleteProcess(long no) {
		//repository.deleteById(no); //존재하든 말든 삭제
		repository.delete(repository.findById(no).orElseThrow());
		//조회해서 존재할때만 삭제
		
	}
	
}
