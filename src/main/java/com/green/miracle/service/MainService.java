package com.green.miracle.service;

import org.springframework.ui.Model;

import com.green.miracle.security.CustomUserDetails;

import jakarta.servlet.http.HttpSession;

public interface MainService {

	void findAllProcess(Model model, CustomUserDetails user);

	void sessionTime(Model model, HttpSession session);

	

}
