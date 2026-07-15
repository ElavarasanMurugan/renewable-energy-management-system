package com.project.solarservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="solar_panel")
@Data
public class Solar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String panel_name;
    private String location;
    private Double capacity_kw;
    private Double current_generation;
    private String status;
}
