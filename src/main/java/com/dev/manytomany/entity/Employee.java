package com.dev.manytomany.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eid;
    private String name;
    private String email;
    private String technicalSkills;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "EMPLOYEE_PROJECT_TABLE",
            joinColumns = {
                    @JoinColumn(name = "employee_id", referencedColumnName = "eid")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "project_id", referencedColumnName = "id")
            }
    )
    private Set<Project> projects= new HashSet<>();

    public Employee(String name, String email, String technicalSkills) {
        this.name = name;
        this.email = email;
        this.technicalSkills = technicalSkills;
    }

    public Employee(Long eid, String name, String email, String technicalSkills) {
        this.eid=eid;
        this.name = name;
        this.email = email;
        this.technicalSkills = technicalSkills;
    }
}

