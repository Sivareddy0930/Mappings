package com.dev.manytomany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {
    private Long id;
    private String name;
    private String email;
    private String technicalSkills;
    Set<ProjectDto> projects;

}
