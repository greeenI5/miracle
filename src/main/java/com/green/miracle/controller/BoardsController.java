package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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
	public String create() {
		
		return "redirect:/board";
	}
	
	@GetMapping("/boards/{no}")
	public String detail() {
		return "/views/board/form";
	}
	
	@PutMapping("/board/{no}")
	public String update() {

		return "redirect:/views/board/form/{no}";
	}
	
	@DeleteMapping("/board/{no}")
	public String delete() {
		
		return "redirect:/views/board/form";
	}
}
