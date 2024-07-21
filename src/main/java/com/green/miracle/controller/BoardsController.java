package com.green.miracle.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.green.miracle.domain.dto.BoardCreateDTO;
import com.green.miracle.domain.dto.BoardDetailDTO;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.BoardService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class BoardsController {
	
	private final BoardService service;
	
	@GetMapping("/boards")
	public String list(Model model) {
		service.findAllProcess(model);
		return "/views/board/list";
	}
	
	@PostMapping("/boards/form")
	public String write(BoardCreateDTO dto, @AuthenticationPrincipal CustomUserDetails user) {
		service.saveProcess(dto, user);
		return "redirect:/boards";
	}
	
	@GetMapping("/boards/form")
	public String write() {
		return "/views/board/form";
	}
	
	//상세 페이지 조회
	@GetMapping("/boards/{no}")
	public String detail(@PathVariable("no") long no, Model model) {
		service.detailProcess(no, model);
		return "/views/board/detail";
	}
	
	@PutMapping("/boards/{no}")
	public String update(@PathVariable("no") long no, BoardDetailDTO dto) {
		service.updateProcess(no, dto);
		return "redirect:/boards/{no}";
	}
	
	@DeleteMapping("/boards/{no}")
	public String delete(@PathVariable("no") long no) {
		service.deleteProcess(no);
		return "redirect:/boards";
	}
}
