package com.dev.manytomany.controller;

import com.dev.manytomany.service.ProjectEmployeeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeProjectRelationController {

    @Autowired
    private ProjectEmployeeRelationService projectEmployeeRelationService;

    @PostMapping("/save-relation")
    public ResponseEntity<Map<String, Object>> addEmployeeProjectRelation(@RequestParam Long projectId, @RequestParam Long employeeId) {
        return projectEmployeeRelationService.saveEmployeeAndProjectIdsRelation(projectId, employeeId);
    }
}
