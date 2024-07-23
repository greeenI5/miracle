package com.green.miracle.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.miracle.domain.entity.PerType;
import com.green.miracle.domain.entity.PerformancePlanEntity;
import com.green.miracle.service.ApprovalService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@RequestMapping("/admin/approval")
@Controller
public class ApprovalController {
	
	private final ApprovalService service;
	
	/*////////////////////////////////////////*/
	/*                  대  기                 */
	/*////////////////////////////////////////*/
	@GetMapping("/plan/request")
	public String RequestList(Model model) { //model 객체 : 응답하는 페이지까지의 범위를 갖고 있음 
		service.findByApprovalRequest(model); // approval = 0 : 결재대기
		return "views/approval/request-list";
	}
	
	@GetMapping("/plan/request/{planNo}")
	public String requestDetail(@PathVariable("planNo") long planNo, Model model) {
		
		//perType 문자열변환
		List<PerType> perType = Arrays.asList(PerType.values());
        model.addAttribute("perType", perType);
        
    	PerformancePlanEntity plan = service.findPlanById(planNo);
        if (plan == null) {
            return "error/404";  // 404 페이지를 반환할 경로
        }
        model.addAttribute("plan", plan);
        return "views/approval/request-view";
    }
	
	@PostMapping("/update/{id}")
    public String updateData(@PathVariable("planNo") Long planNo,
                             @RequestParam("newApproval") int approval,
                             RedirectAttributes redirectAttributes) {
        boolean isUpdated = service.updateData(planNo, approval);
        if (isUpdated) {
            redirectAttributes.addFlashAttribute("message", "Data updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to update data.");
        }
        return "redirect:/admin/approval/plan/request"; // 업데이트 후 리다이렉트할 페이지
    }
	
	/*////////////////////////////////////////*/
	/*                  보  류                 */
	/*////////////////////////////////////////*/
	@GetMapping("/plan/hold")
	public String HoldList(Model model) { //model 객체 : 응답하는 페이지까지의 범위를 갖고 있음 
		service.findByApprovalHold(model); // approval = 1 : 결재보류
		return "views/approval/hold-list";
	}
	
	@GetMapping("/plan/hold/{planNo}")
	public String holdDetail(@PathVariable("planNo") long planNo, Model model) {
		
		//perType 문자열변환
		List<PerType> perType = Arrays.asList(PerType.values());
		model.addAttribute("perType", perType);
		
		PerformancePlanEntity plan = service.findPlanById(planNo);
		if (plan == null) {
			return "error/404";  // 404 페이지를 반환할 경로
		}
		model.addAttribute("plan", plan);
		return "views/approval/view";
	}
	
	/*////////////////////////////////////////*/
	/*                 승  인                  */
	/*////////////////////////////////////////*/
	@GetMapping("/plan/permission")
	public String PermissionList(Model model) { //model 객체 : 응답하는 페이지까지의 범위를 갖고 있음 
		service.findByApprovalPermission(model); // approval = 2 : 결재승인
		return "views/approval/permission-list";
	}
	
	@GetMapping("/plan/permission/{planNo}")
	public String approvalDetail(@PathVariable("planNo") long planNo, Model model) {
		
		//perType 문자열변환
		List<PerType> perType = Arrays.asList(PerType.values());
		model.addAttribute("perType", perType);
		
		PerformancePlanEntity plan = service.findPlanById(planNo);
		if (plan == null) {
			return "error/404";  // 404 페이지를 반환할 경로
		}
		model.addAttribute("plan", plan);
		return "views/approval/view";
	}
	/*////////////////////////////////////////*/
}
