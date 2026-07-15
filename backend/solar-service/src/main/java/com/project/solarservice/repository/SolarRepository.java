package com.project.solarservice.repository;

import com.project.solarservice.entity.Solar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolarRepository extends JpaRepository<Solar,Long> {
}
