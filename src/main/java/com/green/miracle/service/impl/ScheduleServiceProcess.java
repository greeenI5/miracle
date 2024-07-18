package com.green.miracle.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.miracle.domain.dto.ScheduleCreateDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.ScheduleEntity;
import com.green.miracle.domain.repository.ScheduleEntityRepository;
import com.green.miracle.service.ScheduleService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceProcess implements ScheduleService{
	
	private final ScheduleEntityRepository repository; //생성자 DI > 테이블에 접근한 레파지토리가 필요
	
	@Override
	@Transactional
    public void saveProcess(ScheduleCreateDTO dto) {
        // DTO에서 엔티티로 변환하고 저장하기 전에 유효성 검사 등 추가
        if (dto.getStartAt().isAfter(dto.getFinishAt())) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }

        repository.save(dto.toEntity());
    }

	@Override
	public void findProcess(ScheduleEntity scheduleEntity, Model model) {
        List<ScheduleEntity> scheduleList = repository.findAll();
        model.addAttribute("schedule", scheduleList);
	}

}
