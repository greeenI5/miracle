package com.green.miracle.controller.schedule;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.miracle.domain.dto.DateReadDTO;
import com.green.miracle.domain.dto.ScheduleChangeDTO;
import com.green.miracle.domain.dto.ScheduleReadDTO;
import com.green.miracle.domain.dto.ScheduleSaveDTO;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

//@RequestMapping 애노테이션은 이 클래스의 기본 URL 경로를 정의합니다.
@RequiredArgsConstructor
@Controller
public class ScheduleController {
	

	private final ScheduleService service;
	
	@GetMapping("/schedule")
    public String getCalendar(Model model, @AuthenticationPrincipal CustomUserDetails user) {
		
		LocalDate cilckDate = LocalDate.now();
		service.findScheduleProcess(model, cilckDate, user);
        return "/views/schedule/calendar";
    }
	
	@PostMapping("/schedule/date")
	@ResponseBody
	public List<ScheduleReadDTO> readDate(@RequestBody DateReadDTO dateRead, @AuthenticationPrincipal CustomUserDetails user) {
		LocalDate clickDate = dateRead.getDate();
		return service.scheduleChangeProcess(clickDate, user);
	}
	
	// 데이터 존재여부확인
	@PostMapping("/schedule/check-data")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkData(@RequestBody Map<String, String> requestData) {
		
		// 요청 데이터에서 'startAt', 'finishAt', 'schTitle' 값을 추출하여 변수에 저장합니다.
	    // `LocalDate.parse()` 메서드를 사용하여 문자열을 LocalDate 객체로 변환합니다.
        LocalDate startAt = LocalDate.parse(requestData.get("startAt"));
        LocalDate finishAt = LocalDate.parse(requestData.get("finishAt"));
        String schTitle = requestData.get("schTitle");

        boolean hasData = service.existsByStartAtAndFinishAtAndSchTitle(startAt, finishAt, schTitle);

        // 응답을 위해 Map<String, Object> 객체를 생성하고, `hasData` 값을 추가합니다.
        // 이 Map 객체는 클라이언트에게 JSON 형태로 반환됩니다.
        Map<String, Object> response = new HashMap<>();
        response.put("hasData", hasData);

        return ResponseEntity.ok(response);
    }
	
	@PostMapping("/schedule/save")
    public String writeMemo(@ModelAttribute ScheduleSaveDTO dto, @AuthenticationPrincipal CustomUserDetails user) {

        service.saveMemo(dto, user);
        return "redirect:/schedule";
    }
	
	@PostMapping("/schedule/upload")
	public String reWriteMemo(@PathVariable("schNo") Long schNo, ScheduleChangeDTO dto) {
		
		service.updateProcess(schNo, dto);
		
		return "redirect:/schedule";
	}
	
	
	
	
}
