package com.green.miracle.service;

import java.time.LocalDate;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.green.miracle.domain.entity.PerType;

public interface Planservice {

	void findAllProcess(Model model);

	void save(String perType, MultipartFile file);

	void savePlan(String perContent, String employee, String perTitle, PerType perType, LocalDate startAt,
			LocalDate finishAt, String location, LocalDate writeAt, MultipartFile perPoster);
}
