package com.project.batteryservice.repository;

import com.project.batteryservice.entity.BatteryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryHistoryRepository extends JpaRepository<BatteryHistory,Long> {

}
