package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class NoticeController {
	
	@GetMapping("/notices")
	public String list() {
		return "/views/notice/list";
	}
	
	@PostMapping("/admin/notices/form")
	public String create() {
		return "redirect:/notice";
	}
	
	@GetMapping("/notices/{no}")
	public String detail() {
		return "/views/notice/detail";
	}
	
	@PutMapping("/admin/notice/{no}")
	public String update() {	
		return "redirect:/views/notice/detail/{no}";
	}
	
	@DeleteMapping("/admin/notice/{no}")
	public String delete() {	
		return "redirect:/views/notice/detail";
	}
	
	@GetMapping("/board/write")
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	
}
