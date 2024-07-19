package com.green.miracle.controller.hr;

import com.green.miracle.domain.dto.AdminHrSaveDTO;
import com.green.miracle.domain.entity.Position;
import com.green.miracle.domain.entity.Role;
import com.green.miracle.service.AdminHrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String mgmList(Model model) {
        service.ListProcess(1, model);
        return "views/admin/mgm";
    }

    @PostMapping("/admin/hr/mgm")
    public String adminHrSave(@ModelAttribute AdminHrSaveDTO dto, Model model) {
        System.out.println("Received AdminHrSaveDTO: " + dto);
        
        // 부서 코드 유효성 검사
        if (!isValidDepCode(dto.getDepCode())) {
            model.addAttribute("errorMessage", "Invalid department code in DTO");
            model.addAttribute("positions", Position.values()); // Position 열거형 목록
            model.addAttribute("roles", Role.values()); // Role 열거형 목록
            return "views/admin/mgm"; // 오류 메시지를 다시 입력 폼으로 전달
        }
        
        service.SaveProcess(dto);
        return "redirect:/admin/hr/mgm";
    }

    private boolean isValidDepCode(long depCode) {
        // 부서 코드가 0이 아니고, 추가적인 유효성 검사 로직이 있을 경우 포함
        return depCode != 0;
    }
    
    @ResponseBody// -> 성공 시 success 콜백함수의 인자로 전달
	@DeleteMapping("/admin/hr/mgm/{no}")
	public void delete(@PathVariable("no") long no) {
		
		service.deleteProcess(no);
		
	}
    
}
