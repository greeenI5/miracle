package com.green.miracle.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.miracle.domain.entity.PerformancePlanEntity;

public interface PlanEntityRepository extends JpaRepository<PerformancePlanEntity, Long>{

}
