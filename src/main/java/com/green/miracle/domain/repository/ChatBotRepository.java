package com.green.miracle.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.miracle.domain.entity.ChatBotEntity;


@Repository
public interface ChatBotRepository extends JpaRepository<ChatBotEntity, Long> {
}
