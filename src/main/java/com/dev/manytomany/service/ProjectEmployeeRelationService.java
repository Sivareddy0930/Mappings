package com.dev.manytomany.service;

import com.dev.manytomany.exception.RelationAlreadyExistsException;
import com.dev.manytomany.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProjectEmployeeRelationService {

    private  static final String RELATION_EXIST = "Project and employee already mapped with each other";
    private static final String RELATION_SAVED = "Project and employee relation saved successfully";
    private static final String MESSAGE_KEY="Message";
    private static final String STATUS_CODE_KEY="StatusCode";

    @Autowired
    private EmployeeRepository employeeRepository;


    @Transactional
    public ResponseEntity<Map<String ,Object>> saveEmployeeAndProjectIdsRelation(Long projectId, Long employeeId){

        Map<String, Object> response = new HashMap<>();


        Long size = employeeRepository.checkExistenceOfEmployeeProjectRelation(projectId, employeeId);
        if(size>0){
            throw new RelationAlreadyExistsException(RELATION_EXIST);
        }

        employeeRepository.addEmployeeProjectRelation(projectId, employeeId);

        response.put(MESSAGE_KEY,RELATION_SAVED);
        response.put(STATUS_CODE_KEY, HttpStatus.CREATED.value());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
