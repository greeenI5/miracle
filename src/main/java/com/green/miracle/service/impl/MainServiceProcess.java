package com.green.miracle.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.repository.BoardEntityRepository;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.domain.repository.NoticeEntityRepository;
import com.green.miracle.service.MainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceProcess implements MainService{
	
	private final EmployeeEntityRepository employeeRep;
	private final NoticeEntityRepository noticeRep;
	private final BoardEntityRepository boardRep;
	
	public void findAllProcess(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName();
            model.addAttribute("emp", employeeRep.findByEmail(email));
        }
		model.addAttribute("notice", noticeRep.findAll());
		model.addAttribute("board", boardRep.findAll());
		
	}

}
