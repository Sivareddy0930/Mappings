package com.dev.manytomany.service;

import com.dev.manytomany.dto.EmployeeDto;
import com.dev.manytomany.dto.EmployeeResponseDto;
import com.dev.manytomany.dto.ProjectDto;
import com.dev.manytomany.dto.ProjectResponseDto;
import com.dev.manytomany.entity.Employee;
import com.dev.manytomany.entity.Project;
import com.dev.manytomany.exception.NotFoundException;
import com.dev.manytomany.repository.EmployeeRepository;
import com.dev.manytomany.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EmployeeProjectServiceGetData {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private static final String PROJECT_NOT_FOUND = "Project not found" ;
    private static final String EMPLOYEE_NOT_FOUND = "Employee not found" ;
    private static final String RESPONSE_KEY="Data";
    private static final String STATUS_CODE_KEY="StatusCode";


    public ResponseEntity<Map<String,Object>> getEmployeeDataByEmail(String employeeEmail) {

        Map<String, Object> mapResponse = new HashMap<>();

        Set<ProjectDto> projects = new HashSet<>();


        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeEmail);
        if (!optionalEmployee.isPresent()) {
            throw new NotFoundException(EMPLOYEE_NOT_FOUND);
        }

        Long eid = optionalEmployee.get().getEid();

        List<Long> projectIds;




        projectIds = employeeRepository.findProjectIdsByEmployeeId(eid);



        for ( Long projectId : projectIds) {
            Optional<Project> byId = projectRepository.findById(projectId);
            if (byId.isPresent()) {

                Project project = byId.get();
                ProjectDto projectDto = new ProjectDto(project.getId(),project.getName(),project.getDescription());
                projects.add(projectDto);
            }
        }

        EmployeeResponseDto response = new EmployeeResponseDto();
        Employee employee = optionalEmployee.get();
        response.setId(employee.getEid());
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        response.setTechnicalSkills(employee.getTechnicalSkills());

        response.setProjects(projects);
        mapResponse.put(STATUS_CODE_KEY,HttpStatus.OK.value());
        mapResponse.put(RESPONSE_KEY,response);
        return ResponseEntity.status(HttpStatus.OK).body(mapResponse);

    }

    public ResponseEntity<Map<String,Object>> getProjectDataById(Long id){

        Map<String, Object> mapResponse = new HashMap<>();
        List<Long> employeeIds;
        Set<EmployeeDto> employees = new HashSet<>();


        Optional<Project> OptionalProject;
        OptionalProject = projectRepository.findById(id);
        Project project = OptionalProject.get();


        if(!OptionalProject.isPresent()){
            throw new NotFoundException(PROJECT_NOT_FOUND);
        }




        employeeIds = employeeRepository.findEmployeeIdsByProjectId(id);


        for (Long employeeId : employeeIds) {

            Optional<Employee> byId = employeeRepository.findById(employeeId);
            Employee employee = byId.get();

            EmployeeDto employeeDto = new EmployeeDto(employee.getEid(),employee.getName(),employee.getEmail(),employee.getTechnicalSkills());
            employees.add(employeeDto);
        }

        ProjectResponseDto response = new ProjectResponseDto();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setEmployeeDto(employees);


        mapResponse.put(STATUS_CODE_KEY,HttpStatus.OK.value());
        mapResponse.put(RESPONSE_KEY,response);
        return ResponseEntity.status(HttpStatus.OK).body(mapResponse);


    }



    public ResponseEntity<Map<String, Object>> getAllProjects() {
        List<Project> projectsList = projectRepository.findAll();

        List<Map<String, Object>> projectDetails = new ArrayList<>();

        for (Project project : projectsList) {
            Map<String, Object> projectData = new HashMap<>();

            projectData.put("id", project.getId());
            projectData.put("name", project.getName());
            projectData.put("description", project.getDescription());

            projectDetails.add(projectData);
        }

        Map<String, Object> mapResponse = new HashMap<>();
        mapResponse.put(STATUS_CODE_KEY, HttpStatus.OK.value());
        mapResponse.put(RESPONSE_KEY, projectDetails);

        return ResponseEntity.status(HttpStatus.OK).body(mapResponse);
    }



    public ResponseEntity<Map<String,Object>> getAllEmployees(){

        List<Employee> employeesList = employeeRepository.findAll();

        List<Map<String, Object>> employeeDetails = new ArrayList<>();

        for (Employee employee : employeesList) {
            Map<String, Object> employeeData = new HashMap<>();

            // Only add employee details without the projects
            employeeData.put("eid", employee.getEid());
            employeeData.put("name", employee.getName());
            employeeData.put("email", employee.getEmail());
            employeeData.put("technicalSkills", employee.getTechnicalSkills());

            // Add the employee data to the list
            employeeDetails.add(employeeData);
        }

        Map<String, Object> mapResponse = new HashMap<>();

        mapResponse.put(STATUS_CODE_KEY,HttpStatus.OK.value());
        mapResponse.put(RESPONSE_KEY,employeeDetails);

        return ResponseEntity.status(HttpStatus.OK).body(mapResponse);

    }


}
