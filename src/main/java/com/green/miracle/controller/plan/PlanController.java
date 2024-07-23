/*
 * package com.green.miracle.controller.plan;
 * 
 * import java.io.File; import java.io.IOException; import java.nio.file.Paths;
 * import java.time.LocalDate;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.security.core.annotation.AuthenticationPrincipal; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RequestPart; import
 * org.springframework.web.multipart.MultipartFile;
 * 
 * import com.green.miracle.domain.dto.PlanCreateDTO; import
 * com.green.miracle.domain.entity.EmployeeEntity; import
 * com.green.miracle.domain.entity.PerType; import
 * com.green.miracle.security.CustomUserDetails; import
 * com.green.miracle.service.EmployeeService; import
 * com.green.miracle.service.PlanService; import lombok.RequiredArgsConstructor;
 * 
 * @RequiredArgsConstructor
 * 
 * @RequestMapping("/performance")
 * 
 * @Controller public class PlanController {
 * 
 * private final PlanService service;
 * 
 * @Value("${upload.path}") // 설정 파일에서 파일 저장 경로를 읽어옵니다. private String
 * uploadPath;
 * 
 * //////////////////////////////////////// 목 록
 * ////////////////////////////////////////
 * 
 * @GetMapping("/plan") public String list(Model model) { //model 객체 : 응답하는
 * 페이지까지의 범위를 갖고 있음 service.findAllProcess(model); return "views/plan/list"; }
 * 
 * 
 * //////////////////////////////////////// 기 획 서 작 성
 * ////////////////////////////////////////
 * 
 * @GetMapping("/plan/form") // 페이지 이동 public String writePage() { return
 * "views/plan/write"; }
 * 
 * @PostMapping("/plan") public String write(PlanCreateDTO dto,
 * 
 * @AuthenticationPrincipal CustomUserDetails user,
 * 
 * @RequestParam("perPoster") MultipartFile perPoster) throws IOException {
 * 
 * // 파일 저장 처리 if (!perPoster.isEmpty()) { // 저장할 파일 경로 생성 String fileName =
 * perPoster.getOriginalFilename(); File targetFile = new File(uploadPath,
 * fileName);
 * 
 * // 파일 저장 perPoster.transferTo(targetFile);
 * 
 * // DTO에 파일 경로 설정 String filePath = Paths.get(uploadPath,
 * fileName).toString(); dto.setPerPoster(filePath); }
 * 
 * // 서비스에 파일과 DTO를 전달하여 처리 service.savePlan(dto, user, perPoster);
 * 
 * return "redirect:/performance/plan"; }
 * //////////////////////////////////////// 상 세 보 기
 * ////////////////////////////////////////
 * 
 * @GetMapping("/plan/{planNo}") // 상세페이지 출력 public String getdetail(Model
 * model) { service.findAllProcess(model); return "views/plan/view"; }
 * 
 * 
 * }
 */