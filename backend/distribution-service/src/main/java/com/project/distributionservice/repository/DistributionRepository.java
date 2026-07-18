package com.project.distributionservice.repository;

import com.project.distributionservice.entity.DistributionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributionRepository extends JpaRepository<DistributionHistory,Long> {
}
