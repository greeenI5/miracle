package com.green.miracle.service;

import org.springframework.ui.Model;

public interface HrService {

	void HrListProcess(Model model);

	void detailProcess(long no, Model model);

}
