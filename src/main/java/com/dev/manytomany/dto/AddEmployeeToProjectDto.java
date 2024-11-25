package com.dev.manytomany.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddEmployeeToProjectDto {
    private Long projectId;
    private EmployeeDto employeeDto;
}
