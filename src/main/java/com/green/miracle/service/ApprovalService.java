package com.green.miracle.service;

import org.springframework.ui.Model;

import com.green.miracle.domain.entity.PerformancePlanEntity;

public interface ApprovalService {

	void findByApprovalRequest(Model model);

	void findByApprovalHold(Model model);

	void findByApprovalPermission(Model model);

	PerformancePlanEntity findPlanById(long planNo);

	void savePlan(PerformancePlanEntity plan);

}
