package com.project.windservice.repository;

import com.project.windservice.entity.WindTurbine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WindTurbineRepository extends JpaRepository<WindTurbine,Long> {
}
