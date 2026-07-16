package com.project.windservice.repository;

import com.project.windservice.entity.WindGenerationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WindGenerationRepository extends JpaRepository<WindGenerationHistory,Long> {
    List<WindGenerationHistory> findByWindTurbineId(Long turbineId);
}
