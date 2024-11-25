package com.dev.manytomany.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeDto {
    private Long eid;
    private String name;
    private String email;
    private String technicalSkills;
}
