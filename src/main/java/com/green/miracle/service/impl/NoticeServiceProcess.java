package com.green.miracle.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.NoticeCreateDTO;
import com.green.miracle.domain.dto.NoticeDetailDTO;
import com.green.miracle.domain.entity.NoticeEntity;
import com.green.miracle.domain.repository.NoticeEntityRepository;
import com.green.miracle.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeServiceProcess implements NoticeService{
	
	private final NoticeEntityRepository repository;
	
	@Override
	public void findAllProcess(Model model) {
		model.addAttribute("list", repository.findAll());
		
	}

	@Override
	public void saveProcess(NoticeCreateDTO dto) {
		
		repository.save(dto.toEntity());
		
	}

	@Override
	public void detailProcess(long no, Model model) {
		//상세정보 조회해서 model에 담아라
		NoticeEntity result = repository.findById(no).orElseThrow();
		model.addAttribute("detail", result.toNoticeDetailDTO());
		
	}

	public void updateProcess(long no, NoticeDetailDTO dto) {
		repository.findById(no).orElseThrow().update(dto);
		
	}

	@Override
	public void deleteProcess(long no) {
		//repository.deleteById(no); //존재하든 말든 삭제
		repository.delete(repository.findById(no).orElseThrow());
		//조회해서 존재할때만 삭제 
		
	}
	
	
	

}
