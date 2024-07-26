package com.green.miracle.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.ui.Model;

import com.green.miracle.domain.dto.ScheduleChangeDTO;
import com.green.miracle.domain.dto.ScheduleReadDTO;
import com.green.miracle.domain.dto.ScheduleSaveDTO;
import com.green.miracle.security.CustomUserDetails;

public interface ScheduleService {

	List<ScheduleReadDTO> scheduleChangeProcess(LocalDate clickDate, CustomUserDetails user);

	void findScheduleProcess(Model model, LocalDate cilckDate, CustomUserDetails user);

	void saveMemo(ScheduleSaveDTO dto, CustomUserDetails user);

	boolean existsByStartAtAndFinishAtAndSchTitle(LocalDate startAt, LocalDate finishAt, String schTitle);

	void updateProcess(Long schNo, ScheduleChangeDTO dto);


}
