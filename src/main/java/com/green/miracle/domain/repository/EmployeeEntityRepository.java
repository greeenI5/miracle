package com.green.miracle.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.miracle.domain.entity.EmployeeEntity;
import com.green.miracle.security.CustomUserDetails;

public interface EmployeeEntityRepository extends JpaRepository<EmployeeEntity, Long> {

	Optional<EmployeeEntity> findByEmail(String email);

	void save(CustomUserDetails user);

}
