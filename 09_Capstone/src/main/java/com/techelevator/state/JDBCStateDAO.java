package com.techelevator.state;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCStateDAO implements StateDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCStateDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(State newState) {
		String sqlInsertState = "INSERT INTO state (abbreviation, name) VALUES(?, ?)";
		jdbcTemplate.update(sqlInsertState, newState.getStateAbbreviation(), newState.getStateName());
	}

	@Override
	public void update(State state) {
		String sql = "UPDATE state SET abbreviation = ?, name = ?";
		jdbcTemplate.update(sql, state.getStateAbbreviation(), state.getStateName());
	}

	@Override
	public void delete(String name) {
		String sql = "DELETE FROM state WHERE name = ? ";
		jdbcTemplate.update(sql, name);
	}

	@Override
	public List<State> getAllStates() {
		List<State> states = new ArrayList<>();
		SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT abbreviation, name ");
		
		while(rows.next()) {
			states.add(mapRowToState(rows));
		}
		return states;
	}

	@Override
	public List<State> findStateByAbbreviation(String abbreviation) {
		List<State> states = new ArrayList<>();
		String sqlFindStateByAbbreviation = "SELECT name FROM state WHERE abbreviation = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindStateByAbbreviation, abbreviation);
		while(results.next()) {
			State selectedState = mapRowToState(results);
			states.add(selectedState);
		}
		return states;
	}
	
	private State mapRowToState(SqlRowSet result) {
		State selectedState;
		selectedState = new State();
		selectedState.setStateAbbreviation(result.getString("abbreviation"));
		selectedState.setStateName(result.getString("name"));
		return selectedState;
	}

}
