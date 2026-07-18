package com.project.batteryservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="battery_storage_history")
@Data
public class BatteryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String operation;
    private Double units;
    @Column(name="operated_at",nullable = false)
    @CurrentTimestamp
    private LocalDateTime operatedAt;

    @ManyToOne
    @JoinColumn(name="battery_id")
    private Battery battery;
}
