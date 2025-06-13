package com.smartmanage.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee extends User {

    @Column(name = "employee_id", nullable = false)
    private Long employee_id;

    @Column(name = "role", nullable = false)
    private String role;
}
