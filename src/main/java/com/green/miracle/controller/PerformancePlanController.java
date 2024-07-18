package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.miracle.domain.dto.PlanCreateDTO;
import com.green.miracle.service.Planservice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PerformancePlanController {
	
	private final Planservice service;
	
	/*////////////////////////////////////////*/
	/*                  목  록                 */
	/*////////////////////////////////////////*/
	@GetMapping("/performance/plan") // 페이지 이동
	public String list() {
		return "views/plan/list";
	}
	
	@GetMapping("/list") // 목록 출력
	public String list(Model model) { //model 객체 : 응답하는 페이지까지의 범위를 갖고 있음 
		service.findAllProcess(model);
		return "views/plan/list"; //templates에서 시작하기 때문에 그 밑에서부터 주소가 시작
	}
	
	
	/*////////////////////////////////////////*/
	/*             기 획 서 작 성             */
	/*////////////////////////////////////////*/
	@GetMapping("/performance/plan/form") // 페이지 이동
	public String writePage() {
		return "views/plan/write";
	}
	
	@PostMapping("/performance/plans/{plan_no}")
	public String write (PlanCreateDTO dto) {
		service.saveProcess(dto);
		return "redirect:/performance/plan";
	}
	
	/*////////////////////////////////////////*/
	/*               상 세 보 기                */
	/*////////////////////////////////////////*/
	@GetMapping("/performance/plan/{plan_no}") //페이지이동
	public String view() {
		return "views/plan/view";
	}
	
	
	@GetMapping("/detail") // 상세페이지 출력
	public String getdetail(Model model) {
		service.findAllProcess(model);
		return "views/plan/view";
	}
	
	
}
