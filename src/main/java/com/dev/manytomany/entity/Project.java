package com.dev.manytomany.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String description;

//    @ManyToMany(mappedBy = "projects", cascade =  CascadeType.ALL,fetch = FetchType.LAZY)

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "EMPLOYEE_PROJECT_TABLE" ,
            joinColumns ={
                    @JoinColumn(name = "project_id", referencedColumnName = "id")
            } ,
            inverseJoinColumns = {
            @JoinColumn(name = "employee_id", referencedColumnName = "eid")
            }
    )
    private Set<Employee> employees;


}
