package com.dev.manytomany.service;

import com.dev.manytomany.exception.NotFoundException;
import com.dev.manytomany.repository.EmployeeRepository;
import com.dev.manytomany.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeProjectDeleteService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private static final String EMPLOYEE_DELETED = "Employee deleted successfully with ID %s";
    private static final String PROJECT_DELETED = "Project deleted successfully with ID %s";
    private static final String MESSAGE_KEY="Message";
    private static final String STATUS_CODE_KEY="StatusCode";
    private static final String EMPLOYEE_NOT_FOUND = "Employee not found with ID:" ;
    private static final String PROJECT_NOT_FOUND = "Project not found with ID:" ;


    @Transactional
    public ResponseEntity<Map<String,Object>> deleteEmployeeById(Long employeeId) {

        Map<String, Object> response = new HashMap<>();

        employeeRepository.findById(employeeId).orElseThrow(
                ()->new NotFoundException(EMPLOYEE_NOT_FOUND + employeeId)
        );

        employeeRepository.deleteEmployeeFromEmp_Proj_Relation(employeeId);

            employeeRepository.deleteEmployeeById(employeeId);

        response.put(MESSAGE_KEY, String.format(EMPLOYEE_DELETED,employeeId));
        response.put(STATUS_CODE_KEY, HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @Transactional
    public ResponseEntity<Map<String,Object>> deleteProjectById(Long projectId) {

        Map<String, Object> response = new HashMap<>();

        projectRepository.findById(projectId).orElseThrow(
                ()->new NotFoundException(PROJECT_NOT_FOUND + projectId)
        );

            employeeRepository.deleteProjectFromEmp_Proj_Relation(projectId);
            projectRepository.deleteProjectById(projectId);

        response.put(MESSAGE_KEY, String.format(PROJECT_DELETED,projectId));
        response.put(STATUS_CODE_KEY, HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
