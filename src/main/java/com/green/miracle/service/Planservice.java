package com.green.miracle.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.PlanCreateDTO;

public interface Planservice {

	void findAllProcess(Model model);

	void saveProcess(PlanCreateDTO dto);
}
