package com.green.miracle.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.miracle.domain.entity.CommentEntity;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, Long> {

}
