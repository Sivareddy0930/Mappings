package com.dev.manytomany;

import com.dev.manytomany.entity.Employee;
import com.dev.manytomany.repository.EmployeeRepository;
import com.dev.manytomany.service.EmployeeProjectServiceGetData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ManytoManyAppApplicationTests {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeProjectServiceGetData serviceGetData;

	@BeforeEach
	public void setUp() {

		// Mocking repository behavior
		Employee employee1 = new Employee("Napo", "Napo@example.com", "Java");
		employee1.setEid(1L);

		Employee employee2 = new Employee("Qwee", "Qwee@example.com", "Spring");
		employee2.setEid(2L);

		when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
	}

	@Test
	public void testGetAllEmployees() {
		// Call the service method
		ResponseEntity<Map<String, Object>> response = serviceGetData.getAllEmployees();

		// Validate response status
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// Validate response body
		Map<String, Object> employees = response.getBody();
		List<Map<String, Object>> body = (List<Map<String, Object>>) response.getBody().get("response");
		assertEquals(2, employees.size());

		// Validate first employee
		List<Employee> employee1 = (List<Employee>) employees.get("Data");
		assertEquals(1L, employee1.get(0).getEid());
		assertEquals("Napo", employee1.get(0).getName());
		assertEquals("Napo@example.com", employee1.get(0).getEmail());
		assertEquals("Java", employee1.get(0).getTechnicalSkills());

//		// Validate second employee
//		Employee employee2 = (Employee) employees.get("Data");
//		assertEquals(1L, employee2.getEid());
//		assertEquals("Napo", employee2.getName());
//		assertEquals("Napo@example.com", employee2.getEmail());
//		assertEquals("Java", employee1.getTechnicalSkills());
	}
}
