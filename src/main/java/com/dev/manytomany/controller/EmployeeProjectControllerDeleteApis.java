package com.dev.manytomany.controller;

import com.dev.manytomany.service.EmployeeProjectDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeProjectControllerDeleteApis {
    @Autowired
    EmployeeProjectDeleteService service;

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable Long id){
        return service.deleteEmployeeById(id);
    }

    @DeleteMapping("/deleteProject/{id}")
    public ResponseEntity<Map<String, Object>> deleteProject(@PathVariable Long id){
       return service.deleteProjectById(id);
    }

}
