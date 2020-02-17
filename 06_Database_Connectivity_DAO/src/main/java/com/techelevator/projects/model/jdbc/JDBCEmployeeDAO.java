package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.EmployeeDAO;

public class JDBCEmployeeDAO implements EmployeeDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCEmployeeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
		public List<Employee> getAllEmployees() {
			List<Employee> employees = new ArrayList<>();
			
			String emp = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee";
			SqlRowSet results = jdbcTemplate.queryForRowSet(emp);
			
			while(results.next()) {
				employees.add(mapRowToEmployee (results));
			}
			return employees;
		}
	

	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		List<Employee> employees = new ArrayList<>();
		
		String emp =  "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee WHERE first_name = ? AND last_name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(emp, firstNameSearch, lastNameSearch);
		
		while(results.next()) {
			employees.add(mapRowToEmployee (results));
		}
		return employees;
	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(long id) {
	List<Employee> employees = new ArrayList<>();
		
		String emp =  "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee WHERE first_name = ? AND last_name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(emp, id);
		
		while(results.next()) {
			employees.add(mapRowToEmployee (results));
		}
		return employees;
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
	List<Employee> employees = new ArrayList<>();
		
		String emp =  "SELECT * FROM employee LEFT JOIN project_employee ON employee.employee_id = project_employee.employee_id WHERE project_employee.employee_id IS NULL ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(emp);
		
		while(results.next()) {
			employees.add(mapRowToEmployee (results));
		}
		return employees;
	}
	
	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
	List<Employee> employees = new ArrayList<>();
		
		String emp =  "SELECT * FROM employee JOIN project_employee ON employee.employee_id = project_employee.employee_id WHERE project_employee.employee_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(emp, projectId);
		
		while(results.next()) {
			employees.add(mapRowToEmployee (results));
		}
		return employees;
	}

	@Override
	public void changeEmployeeDepartment(Long employeeId, Long departmentId) {
		String sqlUpdateDept = "UPDATE employee SET department_id = ? WHERE employee_id = ? ";
		jdbcTemplate.update(sqlUpdateDept, departmentId, employeeId);
	}
	
	private Employee mapRowToEmployee(SqlRowSet results) {
		Employee employee = new Employee();
		
		employee.setId(results.getLong("employee_id"));
		employee.setFirstName(results.getString("first_name"));
		employee.setLastName(results.getString("last_name"));

		return employee;
		
	}
	
	private long getNextDepartmentId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_department_id')");

		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new department");
		}
	}

}
