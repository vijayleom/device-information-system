package com.devices.store.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Device Java Persistence API - Entity.
 */
@Entity
@Table(name = "device",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "brand"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Device name cannot be blank")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Brand cannot be blank")
    @Column(nullable = false)
    private String brand;

    @NotBlank(message = "State is required")
    @Column(nullable = false)
    private String state; // IU for "in-use", AV for "available", IA for "in-active" .

    @CreationTimestamp
    @Column(name = "creation_time", nullable = false, updatable = false)
    private Instant creationTime;

    @UpdateTimestamp
    @Column(name = "last_patched_time")
    private Instant lastPatchedTime;
}