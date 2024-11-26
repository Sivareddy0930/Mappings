package com.dev.manytomany;

import com.dev.manytomany.entity.Employee;
import com.dev.manytomany.repository.EmployeeRepository;
import com.dev.manytomany.service.EmployeeProjectService;
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

	@InjectMocks
	private EmployeeProjectService employeeProjectService;


	@BeforeEach
	public void setUp() {

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
		assertEquals(2, employees.size());

		List<Object> data = (List<Object>) employees.get("Data");
		System.out.println(data);


		// Validate first employee
		Object o = data.get(0);
		System.out.println(o);
		Map<String, Object> map = (Map<String, Object>) o;
		Employee employee1 = new Employee(
				(Long) map.get("eid"),
				(String) map.get("name"),
				(String) map.get("email"),
				(String) map.get("technicalSkills")
		);


		assertEquals(1L, employee1.getEid());
		assertEquals("Napo", employee1.getName());
		assertEquals("Napo@example.com", employee1.getEmail());
		assertEquals("Java", employee1.getTechnicalSkills());


		Object o1 = data.get(1);
		System.out.println(o1);
		Map<String, Object> map1 = (Map<String, Object>) o1;
		Employee employee2 = new Employee(
				(Long) map1.get("eid"),
				(String) map1.get("name"),
				(String) map1.get("email"),
				(String) map1.get("technicalSkills")
		);


		assertEquals(2L, employee2.getEid());
		assertEquals("Qwee", employee2.getName());
		assertEquals("Qwee@example.com", employee2.getEmail());
		assertEquals("Spring", employee2.getTechnicalSkills());

	}

	@Test
	public void saveEmployeeTest(){
		Employee employee = new Employee(1L,"ram","ram@gmail.com","java");

		when(employeeRepository.save(employee)).thenReturn(employee);

		ResponseEntity<Map<String, Object>> response = employeeProjectService.saveEmployee(employee);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Employee created successfully with Employee Id: "+1, response.getBody().get("Message"));

	}
}
