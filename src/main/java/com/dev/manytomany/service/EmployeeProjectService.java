package com.dev.manytomany.service;

import com.dev.manytomany.dto.AddEmployeeToProjectDto;
import com.dev.manytomany.dto.AddProjectToEmployeeDto;
import com.dev.manytomany.dto.EmployeeDto;
import com.dev.manytomany.entity.Employee;
import com.dev.manytomany.entity.Project;
import com.dev.manytomany.exception.NotFoundException;
import com.dev.manytomany.repository.EmployeeRepository;
import com.dev.manytomany.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Slf4j
@Service
public class EmployeeProjectService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

//    @Autowired
//    private EntityManager entityManager;

//    @Autowired
//    private MessageSource messageSource;

    private static final String EMPLOYEE_CREATED = "Employee created successfully with Employee Id: ";
    private static final String PROJECT_CREATED = "Project created successfully with project Name ";
    private static final String PROJECT_EMPLOYEES_CREATED = "Project and employees created successfully";
    private static final String EMPLOYEE_PROJECTS_CREATED = "Employee and projects created successfully";
    private static final String PROJECT_ADDED_TO_EMPLOYEE = "Project added to Employee successfully";
    private static final String EMPLOYEE_ADDED_TO_PROJECT = "Employee added to project successfully";
    private static final String PROJECT_NOT_FOUND = "Project not found with ID:" ;
    private static final String EMPLOYEE_NOT_FOUND = "Employee not found with ID:" ;
    private static final String MESSAGE_KEY="Message";
    private static final String STATUS_CODE_KEY="StatusCode";
    private static final String EMPLOYEE_ALREADY_EXIST = "Employee already exists with email ";
    private static final String PROJECT_ALREADY_EXIST = "Project already exists with Name ";
    private static final String EMPLOYEE_UPDATED = "Employee Updated With Id: ";
    private static final String PROJECT_UPDATED = "Project Updated With Id: ";



    @Transactional
    public ResponseEntity<Map<String,Object>> saveEmployee(Employee employee) {
        Map<String, Object> response = new HashMap<>();
        Optional<Employee> byEmail = employeeRepository.findByEmail(employee.getEmail());
        if (byEmail.isPresent()) {
            response.put(MESSAGE_KEY, EMPLOYEE_ALREADY_EXIST+employee.getEmail());
            response.put(STATUS_CODE_KEY, String.valueOf(HttpStatus.CONFLICT.value()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Employee savedEmployee = employeeRepository.save(employee);


        response.put(MESSAGE_KEY,EMPLOYEE_CREATED.concat((savedEmployee.getEid()).toString()));
        response.put(STATUS_CODE_KEY, HttpStatus.CREATED.value());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional
    public ResponseEntity<Map<String,Object>> saveProject(Project project) {

        Map<String, Object> response = new HashMap<>();
        Optional<Project> byName = projectRepository.findByName(project.getName());

        if (byName.isPresent()) {
            response.put(MESSAGE_KEY, PROJECT_ALREADY_EXIST+project.getName());
            response.put(STATUS_CODE_KEY, String.valueOf(HttpStatus.CONFLICT.value()));
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Project savedProject = projectRepository.save(project);

        response.put(MESSAGE_KEY,PROJECT_CREATED.concat((savedProject.getName())));
        response.put(STATUS_CODE_KEY, HttpStatus.CREATED.value());
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Transactional
    public ResponseEntity<Map<String,Object>> saveProjectWithEmployees(Project project) {

        Map<String, Object> response = new HashMap<>();
        projectRepository.save(project);

        response.put(MESSAGE_KEY,PROJECT_EMPLOYEES_CREATED);
        response.put(STATUS_CODE_KEY, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> saveEmployeeWithProjects(Employee employee) {
        Map<String, Object> response = new HashMap<>();
        employeeRepository.save(employee);

        response.put(MESSAGE_KEY,EMPLOYEE_PROJECTS_CREATED);
        response.put(STATUS_CODE_KEY, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Transactional
    public ResponseEntity<Map<String,Object>> addEmployeeToProjectBasedOnId(AddEmployeeToProjectDto addEmployeeToProjectDto) {

        Map<String, Object> response = new HashMap<>();

        projectRepository.findById(addEmployeeToProjectDto.getProjectId())
                .orElseThrow(() -> new NotFoundException(PROJECT_NOT_FOUND+ addEmployeeToProjectDto.getProjectId()));

        EmployeeDto employeeDto = addEmployeeToProjectDto.getEmployeeDto();

        Employee emp1 = new Employee(employeeDto.getName(),employeeDto.getEmail(),employeeDto.getTechnicalSkills());

        Employee savedEmployee = employeeRepository.save(emp1);

        employeeRepository.addEmployeeProjectRelation(addEmployeeToProjectDto.getProjectId(),savedEmployee.getEid());

        response.put(MESSAGE_KEY, EMPLOYEE_ADDED_TO_PROJECT);
        response.put(STATUS_CODE_KEY, HttpStatus.CREATED.value());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Transactional
    public ResponseEntity<Map<String,Object>> addProjectToEmployeeBasedOnId(AddProjectToEmployeeDto addProjectToEmployeeDto) {

        Map<String, Object> response = new HashMap<>();

        employeeRepository.findById(addProjectToEmployeeDto.getEmployeeId()).orElseThrow(
                ()->new NotFoundException(EMPLOYEE_NOT_FOUND + addProjectToEmployeeDto.getEmployeeId())
        );

        Project project = new Project(addProjectToEmployeeDto.getProjectDto().getName(), addProjectToEmployeeDto.getProjectDto().getDescription());
        Project savedProject = projectRepository.save(project);

        employeeRepository.addEmployeeProjectRelation(savedProject.getId(),addProjectToEmployeeDto.getEmployeeId());

        response.put(MESSAGE_KEY, PROJECT_ADDED_TO_EMPLOYEE);
        response.put(STATUS_CODE_KEY, HttpStatus.CREATED.value());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @Transactional
    public ResponseEntity<Map<String,Object>> updateEmployee(Long id,Employee employee) {
        Map<String, Object> response = new HashMap<>();
        Optional<Employee> byEmail = employeeRepository.findById(id);
        if (byEmail.isPresent()) {

            Employee existingEmployee = byEmail.get();
            existingEmployee.setName(employee.getName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setTechnicalSkills(employee.getTechnicalSkills());

            employeeRepository.save(existingEmployee);

            response.put(MESSAGE_KEY, EMPLOYEE_UPDATED.concat((existingEmployee.getEid()).toString()));
            response.put(STATUS_CODE_KEY, HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        throw new NotFoundException(EMPLOYEE_NOT_FOUND.concat((id).toString()));

    }

    @Transactional
    public ResponseEntity<Map<String,Object>> updateProject(Long id,Project project) {

        Map<String, Object> response = new HashMap<>();
        Optional<Project> byName = projectRepository.findById(id);

        if (byName.isPresent()) {
            Project existingProject = byName.get();
            existingProject.setName(project.getName());
            existingProject.setDescription(project.getDescription());

            projectRepository.save(existingProject);

            response.put(MESSAGE_KEY, PROJECT_UPDATED.concat((existingProject.getId()).toString()));
            response.put(STATUS_CODE_KEY, HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        throw new NotFoundException(PROJECT_NOT_FOUND.concat((id).toString()));

    }





}


