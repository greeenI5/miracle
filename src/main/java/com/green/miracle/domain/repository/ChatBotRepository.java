package com.green.miracle.domain.repository;


import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.miracle.domain.dto.ChatBotCategoryDTO;
import com.green.miracle.domain.entity.ChatBotCategoryEntity;


@Repository
public interface ChatBotRepository extends JpaRepository<ChatBotCategoryEntity, Long> {
    
	//List<ChatBotCategoryEntity> findAllByType(long type);

	List<ChatBotCategoryEntity> findAllByParent_cNo(long type);
}

