package com.green.miracle.controller.plan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.green.miracle.domain.dto.PlanCreateDTO;
import com.green.miracle.domain.entity.PerType;
import com.green.miracle.domain.entity.PerformancePlanEntity;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.PlanService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/performance")
public class PlanController {
    
    private final PlanService service;
    
    @Value("${upload.path}")
    private String uploadPath;
    
    /*////////////////////////////////////////*/
	/*                  목  록                 */
	/*////////////////////////////////////////*/
    @GetMapping("/plan")
    public String list(Model model) {
        service.findAllProcess(model);
        return "views/plan/list";
    }
    
    /*////////////////////////////////////////*/
	/*             기 획 서 작 성               */
	/*////////////////////////////////////////*/
    @GetMapping("/plan/form")
    public String writePage() {
        return "views/plan/write";
    }
    
    @PostMapping("/plan")
    public String write(
            @ModelAttribute PlanCreateDTO dto,
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam("perPoster") MultipartFile perPoster) throws IOException {
        
        // 파일 저장 처리
        String filename = null;
        if (perPoster != null && !perPoster.isEmpty()) {
            filename = UUID.randomUUID().toString() + "_" + perPoster.getOriginalFilename();
            Path filePath = Paths.get(uploadPath, filename);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, perPoster.getBytes());
        }
        
        // Save file and plan
        service.saveFile(dto, filename, user);
        /*service.savePlan(dto, user);*/ // '파일이름만'
        
        return "redirect:/performance/plan";
    }
    
    /*////////////////////////////////////////*/
	/*               상 세 보 기                */
	/*////////////////////////////////////////*/
    @GetMapping("/plan/{planNo}")
    public String getdetail(@PathVariable("planNo") long planNo, Model model) {
    	
		//perType 문자열변환
		List<PerType> perType = Arrays.asList(PerType.values());
        model.addAttribute("perType", perType);
    	
    	PerformancePlanEntity plan = service.findPlanById(planNo);
        if (plan == null) {
            return "error/404";  // 404 페이지를 반환할 경로
        }
        model.addAttribute("plan", plan);
        return "views/plan/view";
    }
}
