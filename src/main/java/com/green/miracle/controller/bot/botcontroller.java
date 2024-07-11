package com.green.miracle.controller.bot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class botcontroller {

	@GetMapping("/bot/hello")
	public String list() {
		return "views/bot/hello";
	}
	
	@GetMapping("/bot/search/contact")
	public String contactlist() {
		return "views/bot/search-contact.html";
	}
	
	@GetMapping("/bot/search/schedule")
	public String schedulelist() {
		return "views/bot/search-schedule.html";
	}
	@GetMapping("/bot/search/notice")
	public String noticelist() {
		return "views/bot/search-notice.html";
	}
	
}
