package com.green.miracle.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.miracle.domain.entity.BoardEntity;

public interface BoardEntityRepository extends JpaRepository<BoardEntity, Long> {

}
