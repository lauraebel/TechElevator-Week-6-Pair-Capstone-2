package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.DepartmentDAO;

public class JDBCDepartmentDAO implements DepartmentDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCDepartmentDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Department> getAllDepartments() {
		List<Department> departments = new ArrayList<>();
		
		String dept = "SELECT department_id, name FROM department";
		SqlRowSet results = jdbcTemplate.queryForRowSet(dept);
		
		while(results.next()) {
			departments.add(mapRowToDepartment (results));
		}
		return departments;
	}

	@Override
	public List<Department> searchDepartmentsByName(String nameSearch) {
		List<Department> departments = new ArrayList<>();
		
		String dept = "SELECT department_id, name FROM department WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(dept, nameSearch);
		
		while(results.next()) {
			departments.add(mapRowToDepartment (results));
		}
		return departments;
	}

	@Override
	public void saveDepartment(Department updatedDepartment) {
		String sqlUpdateDept = "UPDATE department SET name = ? WHERE department_id = ? ";
		jdbcTemplate.update(sqlUpdateDept, updatedDepartment.getName(), updatedDepartment.getId());
	}

	@Override
	public Department createDepartment(Department newDepartment) {
		String sqlInsertDept = "INSERT INTO department(department_id, name) "
				+ "VALUES(?, ?)";
		newDepartment.setId(getNextDepartmentId());
		jdbcTemplate.update(sqlInsertDept, newDepartment.getId(), newDepartment.getName());
		return newDepartment;
	}

	@Override
	public Department getDepartmentById(Long id) {
		Department theDept = null;
		String sqlFindDeptById = "SELECT department_id, name FROM department WHERE department_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindDeptById, id);
		
		if(results.next()) {
			theDept = mapRowToDepartment(results);
		}
		return theDept;
	}
	
	private Department mapRowToDepartment(SqlRowSet results) {
		Department department = new Department();
		
		department.setId(results.getLong("department_id"));
		department.setName(results.getString("name"));
		
		return department;
		
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
