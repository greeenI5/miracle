package com.green.miracle.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.repository.ApprovalEntityRepository;
import com.green.miracle.service.Approvalservice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApprovalserviceProcess implements Approvalservice{
	
	private final ApprovalEntityRepository repository; 
	

	@Override
	public void findByApprovalRequest(Model model) {
		model.addAttribute("plans",repository.findByApproval(0));
	}
	
	@Override
	public void findByApprovalHold(Model model) {
		model.addAttribute("plans",repository.findByApproval(1));
	}
	
	@Override
	public void findByApprovalPermission(Model model) {
		model.addAttribute("plans",repository.findByApproval(2));
	}

}
