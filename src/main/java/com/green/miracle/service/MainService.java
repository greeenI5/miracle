package com.green.miracle.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.ui.Model;

import com.green.miracle.domain.dto.DateRequestDTO;
import com.green.miracle.domain.dto.ScheduleDTO;
import com.green.miracle.security.CustomUserDetails;

import jakarta.servlet.http.HttpSession;

public interface MainService {

	void findAllProcess(Model model, CustomUserDetails user);

	void sessionTime(Model model, HttpSession session);

	void todaySchedule(Model model, CustomUserDetails user);

	List<ScheduleDTO> selectedSchedule(LocalDate clickDate, CustomUserDetails user);

	

}
