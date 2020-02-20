package com.techelevator.state;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCStateDAO implements StateDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCStateDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(State newState) {
		
	}

	@Override
	public void update(State state) {
		
	}

	@Override
	public void delete(String name) {
		
	}

	@Override
	public List<State> getAllStates() {
		return null;
	}

	@Override
	public List<State> findStateByName(String name) {
		return null;
	}

	@Override
	public List<State> findStateByAbbreviation(String abbreviation) {
		return null;
	}

}
