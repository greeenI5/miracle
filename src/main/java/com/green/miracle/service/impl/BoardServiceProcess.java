package com.green.miracle.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.repository.BoardEntityRepository;
import com.green.miracle.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceProcess implements BoardService {
	
	private final BoardEntityRepository repository;
	
	@Override
	public void findAllProcess(Model model) {
		model.addAttribute("list", repository.findAll());
	}
	
}
