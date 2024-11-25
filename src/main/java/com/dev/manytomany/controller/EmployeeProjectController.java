package com.dev.manytomany.controller;

import com.dev.manytomany.dto.AddEmployeeToProjectDto;
import com.dev.manytomany.dto.AddProjectToEmployeeDto;
import com.dev.manytomany.entity.Employee;
import com.dev.manytomany.entity.Project;
import com.dev.manytomany.service.EmployeeProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeProjectController {

    @Autowired
    private EmployeeProjectService employeeProjectService;

    @PostMapping("/employee")
    public ResponseEntity<Map<String, Object>> saveEmployee(@RequestBody Employee employee) {

        ResponseEntity<Map<String, Object>> mapResponseEntity = employeeProjectService.saveEmployee(employee);
        return mapResponseEntity;
    }

    @PostMapping("/project")
    public ResponseEntity<Map<String, Object>> saveProject(@RequestBody Project project) {
        ResponseEntity<Map<String, Object>> mapResponseEntity = employeeProjectService.saveProject(project);

        return mapResponseEntity;
    }

    @PostMapping("/projects")
    public ResponseEntity<Map<String, Object>> createProjectWithEmployees(@RequestBody Project project) {
        return employeeProjectService.saveProjectWithEmployees(project);
    }

    @PostMapping("/employees")
    public ResponseEntity<Map<String, Object>> createEmployeeWithProjects(@RequestBody Employee employee) {
        return employeeProjectService.saveEmployeeWithProjects(employee);
    }

    @PostMapping("/addEmployeeToProject")
    public ResponseEntity<Map<String, Object>> addEmployeeToProject(@RequestBody AddEmployeeToProjectDto addEmployeeToProjectDto) {
        return employeeProjectService.addEmployeeToProjectBasedOnId(addEmployeeToProjectDto);
    }

    @PostMapping("/addProjectToEmployee")
    public ResponseEntity<Map<String, Object>> addProjectToEmployee(@RequestBody AddProjectToEmployeeDto addProjectToEmployeeDto) {
        return employeeProjectService.addProjectToEmployeeBasedOnId(addProjectToEmployeeDto);
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable Long id,@RequestBody Employee employee) {

        ResponseEntity<Map<String, Object>> mapResponseEntity = employeeProjectService.updateEmployee(id,employee);
        return mapResponseEntity;
    }

    @PutMapping("/updateProject/{id}")
    public ResponseEntity<Map<String, Object>> updateProject(@PathVariable Long id,@RequestBody Project project) {
        ResponseEntity<Map<String, Object>> mapResponseEntity = employeeProjectService.updateProject(id,project);

        return mapResponseEntity;
    }


}