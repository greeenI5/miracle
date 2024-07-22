package com.green.miracle.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.green.miracle.domain.dto.PlanCreateDTO;
import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.repository.EmployeeEntityRepository;
import com.green.miracle.domain.repository.PlanEntityRepository;
import com.green.miracle.security.CustomUserDetails;
import com.green.miracle.service.FileService;
import com.green.miracle.service.PlanService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlanServiceProcess implements PlanService {

	private final PlanEntityRepository repository;
    private final EmployeeEntityRepository EmpRepository;
    @Autowired
    private FileService fileService;
    
    @Override
    public void findAllProcess(Model model) {
        model.addAttribute("plans", repository.findAll());
    }

    @Override
    public void savePlan(PlanCreateDTO dto, CustomUserDetails user, MultipartFile perPoster) throws IOException {
    	// 파일 저장
        String filePath = fileService.saveFile(perPoster);

        // 데이터베이스에 저장
        EmployeeEntity employee = EmpRepository.findByEmail(user.getEmail()).orElseThrow();
        dto.setEmployee(employee);
        dto.setPerPoster(filePath);
        repository.save(dto.toEntity());
    }

    private String saveFile(MultipartFile perPoster) throws IOException {
        // 절대 경로로 변경 (예: 프로젝트 루트 디렉토리 기준)
        String uploadDir = "src/main/resources/static/images/poster";
        // 파일명을 UUID로 생성
        String fileName = UUID.randomUUID().toString() + "_" + perPoster.getOriginalFilename();
        // 파일 경로 생성
        Path filePath = Paths.get(uploadDir, fileName);

        // 디렉터리가 존재하지 않으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 저장
        try (InputStream inputStream = perPoster.getInputStream()) {
            Files.copy(inputStream, filePath);
        }

        return filePath.toString();
    }

	@Override
	public void findPlanByNo(Long planNo, Model model) {
		// TODO Auto-generated method stub
		
	}

    
}
