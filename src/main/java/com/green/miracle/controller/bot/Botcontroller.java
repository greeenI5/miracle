package com.green.miracle.controller.bot;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.green.miracle.domain.dto.BotQuestionDTO;



@Controller
public class Botcontroller {

	@MessageMapping("/question") // '/bot/question'
	public void bot(BotQuestionDTO dto) {
		System.out.println(">>>>>:"+dto);
	}
}
