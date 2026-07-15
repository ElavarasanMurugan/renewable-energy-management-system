package com.project.solarservice.repository;

import com.project.solarservice.entity.SolarGenerationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolarHistoryRepository extends JpaRepository<SolarGenerationEntity,Long> {
    List<SolarGenerationEntity> findBySolarId(Long panelId);
}
