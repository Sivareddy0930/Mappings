package com.dev.manytomany.controller;

import com.dev.manytomany.dto.EmployeeResponseDto;
import com.dev.manytomany.dto.ProjectResponseDto;
import com.dev.manytomany.service.EmployeeProjectServiceGetData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeProjectControllerGetData {

    @Autowired
    private EmployeeProjectServiceGetData employeeProjectServiceGetData;

    @GetMapping("/employeeByEmail/{email}")
    public ResponseEntity<Map<String,Object>> getEmployeeEmail(@PathVariable String email){
        return employeeProjectServiceGetData.getEmployeeDataByEmail(email);
    }


    @GetMapping("/projectById/{id}")
    public ResponseEntity<Map<String,Object>> getProjectById(@PathVariable Long id){
      return employeeProjectServiceGetData.getProjectDataById(id);
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<Map<String,Object>> getEmployees(){
        return employeeProjectServiceGetData.getAllEmployees();
    }

    @GetMapping("/getAllProjects")
    public ResponseEntity<Map<String,Object>> getProjects(){
        return employeeProjectServiceGetData.getAllProjects();
    }
}
