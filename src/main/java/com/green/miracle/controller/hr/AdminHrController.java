package com.green.miracle.controller.hr;

import com.green.miracle.domain.dto.AdminHrSaveDTO;
import com.green.miracle.domain.dto.AdminHrUpdate;
import com.green.miracle.service.AdminHrService;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@ToString
@Controller
@RequiredArgsConstructor
public class AdminHrController {

    private final AdminHrService service;

    @GetMapping("/hr")
    public String list(Model model) {
        service.HrListProcess(model);  // 그룹화된 사원 목록을 처리
        System.out.println(model);
        return "views/admin/hrm";  // Thymeleaf 템플릿의 경로
    }
    
    @GetMapping("/hr/{empNo}")
	public String detail(@PathVariable("empNo") long empNo, Model model) {
		System.out.println(model);
		service.detailProcess(empNo, model);
		return "views/admin/detail";
	}
    
    @GetMapping("/admin/hr/mgm")
    public String mgmList(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, Model model) {
        service.ListProcess(pageNumber, model);
        String nextEmpNo = service.getNextEmployeeNumber();
        model.addAttribute("nextEmpNo", nextEmpNo); // 다음 사원번호 전달
        return "views/admin/mgm"; // 적절한 뷰 이름으로 변경
    }
    
    
    @PostMapping("/admin/hr/mgm")
    public String adminHrSave(@ModelAttribute AdminHrSaveDTO dto) {
            service.SaveProcess(dto);
            return "redirect:/admin/hr/mgm";
    }
    
    @ResponseBody
    @DeleteMapping("/admin/hr/mgm/{empNo}")
    public void delete(@PathVariable("empNo") long empNo) {
            service.deleteProcess(empNo);
    }
    
    @ResponseBody
    @PutMapping("/admin/hr/mgm/{empNo}")
    public void update(@PathVariable("empNo") long empNo, @ModelAttribute AdminHrUpdate dto) {
            service.UpdateProcess(empNo, dto);
    }
}
