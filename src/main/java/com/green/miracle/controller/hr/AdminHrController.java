package com.green.miracle.controller.hr;

import com.green.miracle.domain.dto.AdminHrSaveDTO;
import com.green.miracle.domain.dto.AdminHrUpdate;
import com.green.miracle.service.AdminHrService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminHrController {

    private final AdminHrService service;

    @GetMapping("/admin/hr")
    public String list(Model model) {
        service.findAll(model);
        return "views/admin/hrm";
    }

    @GetMapping("/admin/hr/mgm")
    public String mgmList(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, Model model) {
        service.ListProcess(pageNumber, model);
        String nextEmpNo = service.getNextEmployeeNumber();
        model.addAttribute("nextEmpNo", nextEmpNo); // 다음 사원번호 전달
        return "views/admin/mgm"; // 적절한 뷰 이름으로 변경
    }
    
    @PostMapping("/admin/hr/mgm")
    public String adminHrSave(AdminHrSaveDTO dto) {
        service.SaveProcess(dto);
        return "redirect:/admin/hr/mgm";
    }
    
    @DeleteMapping("/admin/hr/mgm/{empNo}")
    public ResponseEntity<Void> delete(@PathVariable("empNo") long empNo) {
        try {
            service.deleteProcess(empNo);
            return ResponseEntity.noContent().build(); // 성공적으로 처리되면 204 No Content 반환
        } catch (Exception e) {
            // 예외 처리 및 로깅
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/admin/hr/mgm/{empNo}")
    public String update(long empNo, @RequestBody AdminHrUpdate dto) {
    	System.out.println(dto);
            service.UpdateProcess(empNo, dto);
            return "redirect:/admin/hr/mgm";
    }
}
