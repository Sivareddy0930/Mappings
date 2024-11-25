package com.dev.manytomany.repository;

import com.dev.manytomany.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Project p WHERE p.id = :id")
    void deleteProjectById(@Param("id") Long projectId);

    @Query("SELECT p FROM Project p WHERE p.name = :name")
    Optional<Project> findByName(@Param("name") String name);


}

