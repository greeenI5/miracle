package com.green.miracle.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.miracle.domain.entity.ChatBotAnswerEntity;

@Repository
public interface ChatBotAnswerEntityRepository extends JpaRepository<ChatBotAnswerEntity, Long>{
	Optional<ChatBotAnswerEntity> findByKeyword(String keyword);

}
