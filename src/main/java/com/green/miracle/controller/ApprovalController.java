package com.green.miracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.miracle.service.ApprovalService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class ApprovalController {
	
	private final ApprovalService service;
	
	/*////////////////////////////////////////*/
	/*                  목  록                 */
	/*////////////////////////////////////////*/
	@GetMapping("/approval/plan")
	public String RequestList(Model model) { //model 객체 : 응답하는 페이지까지의 범위를 갖고 있음 
		service.findByApprovalRequest(model); // approval = 0 : 결재대기
		return "views/approval/request-list";
	}
	@GetMapping("/approval/plan/hold")
	public String HoldList(Model model) { //model 객체 : 응답하는 페이지까지의 범위를 갖고 있음 
		service.findByApprovalHold(model); // approval = 1 : 결재보류
		return "views/approval/hold-list";
	}
	@GetMapping("/approval/plan/permission")
	public String PermissionList(Model model) { //model 객체 : 응답하는 페이지까지의 범위를 갖고 있음 
		service.findByApprovalPermission(model); // approval = 2 : 결재승인
		return "views/approval/permission-list";
	}
	/*////////////////////////////////////////*/
	/*               상 세 보 기                */
	/*////////////////////////////////////////*/
	@GetMapping("/admin/approval/plan/{plan_no}")
	public String write() {
		return "views/approval/view";
	}
	
}
