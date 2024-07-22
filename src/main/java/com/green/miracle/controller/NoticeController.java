package com.green.miracle.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.miracle.domain.dto.NoticeCreateDTO;
import com.green.miracle.domain.dto.NoticeDetailDTO;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.impl.NoticeServiceProcess;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@Controller
public class NoticeController {
	
	private final NoticeServiceProcess service;
	
	@GetMapping("/notices")
	public String list(Model model) {
		service.findAllProcess(model);
		return "/views/notice/list";
	}
	
	@GetMapping("/admin/notices/form")
	public String noticeForm() {
		return "/views/admin/notices/form";
	}
	
	//
	@PostMapping("/admin/notices/form")
	public String write(NoticeCreateDTO dto, @AuthenticationPrincipal CustomUserDetails user) {
		service.saveProcess(dto, user);
		return "redirect:/notices";
	}
	
	//상세 페이지 조회
	@GetMapping("/notices/{no}")
	public String detail(@PathVariable("no") long no, Model model) {
		service.detailProcess(no, model);
		return "/views/notice/detail";
	}
	
	@PutMapping("/admin/notices/{no}")
	public String update(@PathVariable("no") long no, NoticeDetailDTO dto) {	
		service.updateProcess(no, dto);
		return "redirect:/notices/{no}";
	}
	
	@DeleteMapping("/admin/notices/{no}")
	public String delete(@PathVariable("no") long no) {	
		service.deleteProcess(no);
		return "redirect:/notices";
	}
	
	
	
	
}
