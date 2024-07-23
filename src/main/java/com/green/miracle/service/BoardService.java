package com.green.miracle.service;

import org.springframework.ui.Model;

import com.green.miracle.domain.dto.BoardCreateDTO;
import com.green.miracle.domain.dto.BoardDetailDTO;
import com.green.miracle.domain.dto.BoardUpdateDTO;
import com.green.miracle.security.CustomUserDetails;

public interface BoardService {
	
	void findAllProcess(Model model);
	
	void saveProcess(BoardCreateDTO dto, CustomUserDetails user);

	void detailProcess(long no, Model model);

	void updateProcess(long no, BoardUpdateDTO dto);

	void deleteProcess(long no);
}
