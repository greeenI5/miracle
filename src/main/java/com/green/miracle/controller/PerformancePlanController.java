package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.miracle.domain.dto.PlanCreateDTO;
import com.green.miracle.domain.entity.PerType;
import com.green.miracle.service.Planservice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/performance")
@Controller
public class PerformancePlanController {
	
	private final Planservice service;
	
	/*////////////////////////////////////////*/
	/*                  목  록                 */
	/*////////////////////////////////////////*/
	@GetMapping("/plan")
	public String list(Model model) { //model 객체 : 응답하는 페이지까지의 범위를 갖고 있음 
		service.findAllProcess(model);
		return "views/plan/list";
	}
	
	
	/*////////////////////////////////////////*/
	/*             기 획 서 작 성             */
	/*////////////////////////////////////////*/
	@GetMapping("/plan/form") // 페이지 이동
	public String writePage() {
		return "views/plan/write";
	}
	
	@PostMapping("/plan/{planNo}")
	public String write (
			@RequestParam("perType") String perTypeStr,
			PlanCreateDTO dto) {
		
		PerType perType = PerType.fromString(perTypeStr);
	    dto.setPerType(perType);
		
		service.saveProcess(dto);
		
		return "redirect:/performance/plan";
	}
	
	/*////////////////////////////////////////*/
	/*               상 세 보 기                */
	/*////////////////////////////////////////*/
	
	@GetMapping("/plan/{planNo}") // 상세페이지 출력
	public String getdetail(Model model) {
		service.findAllProcess(model);
		return "views/plan/view";
	}
	
	
}
