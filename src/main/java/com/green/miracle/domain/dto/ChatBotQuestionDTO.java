package com.green.miracle.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class ChatBotQuestionDTO {

	private long qNo;
	private String question;
	private String answer;
	

}
