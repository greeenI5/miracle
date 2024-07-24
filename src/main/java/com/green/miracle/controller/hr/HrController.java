package com.green.miracle.controller.hr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.green.miracle.service.HrService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HrController {
	
	private final HrService service;
	
	@GetMapping("/hr")
	public String list(Model model) {
		service.HrListProcess(model);
		return "views/emp/hrm";
	}
	

	@GetMapping("/hr/{no}")
	public String detail(@PathVariable("no") long no, Model model) {
		service.detailProcess(no, model);
		return "views/emp/detail";
	}
	
	
}
