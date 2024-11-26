package com.dev.manytomany;

import com.dev.manytomany.repository.ProjectRepository;
import com.dev.manytomany.service.EmployeeProjectService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private EmployeeProjectService employeeProjectService;


}
