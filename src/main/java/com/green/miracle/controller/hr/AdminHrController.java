package com.green.miracle.controller.hr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.green.miracle.domain.dto.AdminHrSaveDTO;
import com.green.miracle.service.AdminHrService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminHrController {
	
	private final AdminHrService service;
	
	@GetMapping("/admin/hr")
	public String list(Model model) {
		service.findAll(model);
		return "views/admin/hrm";
	}
	
	@GetMapping("/admin/hr/mgm")
	public String mgmList(Model model) {
		service.ListProcess(model);
		return "views/admin/mgm";
		
	}
	
	@PostMapping("/admin/hr/mgm")
	public String adminHrSave(@ModelAttribute AdminHrSaveDTO dto) {
	    System.out.println("Received AdminHrSaveDTO: " + dto);
	    System.out.println("empNo: " + dto.getEmpNo());
	    System.out.println("name: " + dto.getName());
	    System.out.println("email: " + dto.getEmail());
	    System.out.println("password: " + dto.getPassword());
	    System.out.println("phone: " + dto.getPhone());
	    System.out.println("position: " + dto.getPosition());
	    System.out.println("roles: " + dto.getRoles());
	    System.out.println("depCode: " + dto.getDepCode());

	    if (dto.getDepCode() == 0) {
	        throw new IllegalArgumentException("Invalid department code in DTO");
	    }
	    service.SaveProcess(dto);
	    return "redirect:/admin/hr/mgm"; // 저장 후에 다시 mgm 페이지로 리다이렉트
	}
	
}
