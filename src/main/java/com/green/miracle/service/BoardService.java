package com.green.miracle.service;

import org.springframework.ui.Model;

import com.green.miracle.domain.dto.BoardCreateDTO;
import com.green.miracle.domain.dto.BoardDetailDTO;

public interface BoardService {
	
	void findAllProcess(Model model);
	
	void saveProcess(BoardCreateDTO dto);

	void detailProcess(long no, Model model);

	void updateProcess(long no, BoardDetailDTO dto);

	void deleteProcess(long no);
}
