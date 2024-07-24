package com.green.miracle.service;

import org.springframework.ui.Model;

import com.green.miracle.security.CustomUserDetails;

public interface MailService {

	void mailRead(String code, Model model, CustomUserDetails user) throws Exception;

}
