package com.green.miracle.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.entity.PerformancePlanEntity;
import com.green.miracle.domain.repository.ApprovalEntityRepository;
import com.green.miracle.service.ApprovalService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApprovalServiceProcess implements ApprovalService{
	
	private final ApprovalEntityRepository repository; 
	

	/*//////////////////////////////////////////////////////////////*/
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
	/*//////////////////////////////////////////////////////////////*/
	
	@Override //id로 상세보기매핑
	public PerformancePlanEntity findPlanById(long planNo) {
		
		return repository.findById(planNo).orElse(null);
	}
	/*/////////////////*/
	@Override
	public boolean updateData(Long planNo, int approval) {
		// TODO Auto-generated method stub
		return false;
	}
	/*////////////////*/
	/*
	// approval 컬럼이 1인 데이터를 반환하는 메서드
    public List<PerformancePlanEntity> findByApprovalStatus(int approvalStatus) {
        return repository.findByApproval(approvalStatus);
    }
    
    public boolean updateData(Long id, int newValue) {
        DataEntity data = dataRepository.findById(id).orElse(null);
        if (data == null) {
            return false;
        }
        data.setIntValue(newValue); // int 값 업데이트
        dataRepository.save(data);
        return true;
    }
    
	*/
}
