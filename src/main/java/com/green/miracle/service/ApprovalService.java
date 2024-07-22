package com.green.miracle.service;

import org.springframework.ui.Model;

public interface ApprovalService {

	void findByApprovalRequest(Model model);

	void findByApprovalHold(Model model);

	void findByApprovalPermission(Model model);

}
