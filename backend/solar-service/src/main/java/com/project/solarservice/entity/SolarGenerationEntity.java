package com.project.solarservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "solar_generation_history")
@Data
public class SolarGenerationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double generated_units;
    private LocalDateTime generated_at;

    @ManyToOne
    @JoinColumn(name="panel_id")
    private Solar solar;
}
