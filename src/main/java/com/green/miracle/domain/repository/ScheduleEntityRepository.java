package com.green.miracle.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.miracle.domain.entity.ScheduleEntity;

@Repository
public interface ScheduleEntityRepository extends JpaRepository<ScheduleEntity, Long>{
	
}
