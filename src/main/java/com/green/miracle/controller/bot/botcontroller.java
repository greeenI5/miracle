package com.green.miracle.controller.bot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class botcontroller {

	@GetMapping("/bot")
	public String list() {
		return "views/bot/hello";
	}
	
}
