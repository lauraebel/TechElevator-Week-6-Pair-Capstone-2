package com.techelevator.projects.view;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.DepartmentDAO;
import com.techelevator.projects.model.jdbc.JDBCDepartmentDAO;

public class JdbcDepartmentDaoIntegrationTest {
	
	private DepartmentDAO dao;
	
	private static SingleConnectionDataSource dataSource;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/projects");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Before 
	public void setup() {
		dao = new JDBCDepartmentDAO(dataSource);
	}
	
	@Test
	public void select_department_by_id( ) {
		Department department = new Department();
		department.setName("testName");		
		dao.createDepartment(department);
		
		Department newDepartment = dao.getDepartmentById(department.getId());
		
		Assert.assertNotNull("Was null", newDepartment);
		Assert.assertEquals(department.getName(), newDepartment.getName());
		Assert.assertEquals(department.getId(), newDepartment.getId());
	}
	

}
