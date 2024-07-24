package com.green.miracle.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.domain.entity.PerformancePlanEntity;

public interface PlanEntityRepository extends JpaRepository<PerformancePlanEntity, Long>{

	Optional<PerformancePlanEntity> findByEmployee(EmployeeEntity employee);

}
