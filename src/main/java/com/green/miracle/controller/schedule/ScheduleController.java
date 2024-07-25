package com.green.miracle.controller.schedule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.green.miracle.domain.dto.DateReadDTO;
import com.green.miracle.domain.dto.ScheduleReadDTO;
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
	
	
}
