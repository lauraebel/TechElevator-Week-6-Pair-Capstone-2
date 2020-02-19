 package com.techelevator.projects.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.ProjectDAO;

public class JDBCProjectDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Project> getAllActiveProjects() {
		List<Project> projects = new ArrayList<>();
		
		String proj = "SELECT project_id, name, from_date, to_date FROM project WHERE (from_date IS NOT NULL) AND (to_date >= CURRENT_TIMESTAMP OR to_date IS NULL) ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(proj);
		
		while(results.next()) {
			projects.add(mapRowToProject (results));
		}
		return projects;
	}	
	
	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		String sqlDeleteEmployee = "DELETE FROM project_employee WHERE project_id = ? AND employee_id = ?";
		jdbcTemplate.update(sqlDeleteEmployee, projectId, employeeId);
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		String sqlInsertEmployee = "INSERT INTO project_employee VALUES(?,?)";
		jdbcTemplate.update(sqlInsertEmployee, projectId, employeeId);
	}
	
	private Project mapRowToProject(SqlRowSet results) {
		Project project = new Project();
		
		project.setId(results.getLong("project_id"));
		project.setName(results.getString("name"));
		
		if (project.getStartDate() != null) {
			project.setStartDate(LocalDate.parse(results.getString("from_date")));  
		} else {
			project.setStartDate(null);
		}
		if (project.getEndDate() != null) {
			project.setEndDate(LocalDate.parse(results.getString("to_date")));
		} else {
			project.setEndDate(null);
		}
		return project;
	}

}
