package com.green.miracle.service;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.green.miracle.domain.dto.PlanCreateDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.PerType;
import com.green.miracle.security.CustomUserDetails;

public interface PlanService {

	void findAllProcess(Model model);

	void savePlan(PlanCreateDTO dto, CustomUserDetails user, MultipartFile perPoster) throws IOException;

	void findPlanByNo(Long planNo, Model model);



}
