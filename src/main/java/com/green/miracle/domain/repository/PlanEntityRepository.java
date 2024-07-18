package com.green.miracle.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.miracle.domain.entity.PerformancePlanEntity;
import com.green.miracle.domain.entity.ScheduleEntity;

public interface PlanEntityRepository extends JpaRepository<PerformancePlanEntity, Long>{

}
