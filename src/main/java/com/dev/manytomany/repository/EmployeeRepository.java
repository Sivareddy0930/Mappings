package com.dev.manytomany.repository;

import com.dev.manytomany.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    @Modifying
    @Transactional
    @Query("DELETE FROM Employee e WHERE e.eid = :eid")
    void deleteEmployeeById(@Param("eid") Long eid);


    // Many-to-many relationship management methods

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO EMPLOYEE_PROJECT_TABLE (project_id, employee_id) VALUES (:projectId, :employeeId)", nativeQuery = true)
    void addEmployeeProjectRelation(@Param("projectId") Long projectId, @Param("employeeId") Long employeeId);


    @Query(value = "SELECT COUNT(*) FROM EMPLOYEE_PROJECT_TABLE WHERE project_id = :projectId AND employee_id = :employeeId", nativeQuery = true)
    Long checkExistenceOfEmployeeProjectRelation(@Param("projectId") Long projectId, @Param("employeeId") Long employeeId);

    @Query(value = "SELECT project_id FROM employee_project_table WHERE employee_id = :eid", nativeQuery = true)
    List<Long> findProjectIdsByEmployeeId(@Param("eid") Long eid);


    @Query(value = "SELECT employee_id FROM employee_project_table WHERE project_id = :id", nativeQuery = true)
    List<Long> findEmployeeIdsByProjectId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM EMPLOYEE_PROJECT_TABLE WHERE employee_id = :id", nativeQuery = true)
    void deleteEmployeeFromEmp_Proj_Relation(@Param("id") Long employeeId);



    @Modifying
    @Transactional
    @Query(value = "DELETE FROM EMPLOYEE_PROJECT_TABLE WHERE project_id = :id", nativeQuery = true)
    void deleteProjectFromEmp_Proj_Relation(@Param("id") Long projectId);





}

