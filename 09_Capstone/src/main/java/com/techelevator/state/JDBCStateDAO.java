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
		String sql = "UPDATE state SET name = ? WHERE abbreviation = ? ";
		jdbcTemplate.update(sql, state.getStateName(), state.getStateAbbreviation());
	}

	@Override
	public void delete(String abbreviation) {
		String sql = "DELETE FROM state WHERE abbreviation = ? ";
		jdbcTemplate.update(sql, abbreviation.toString());
	}

	@Override
	public List<State> getAllStates() {
		List<State> states = new ArrayList<>();
		SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT abbreviation, name FROM state ");
		
		while(rows.next()) {
			states.add(mapRowToState(rows));
		}
		return states;
	}

	@Override
	public State findStateByAbbreviation(String abbreviation) {
		State selectedState = null;
		String sqlFindByStateAbbreviation = "SELECT abbreviation, name "
				+ "FROM state " + "WHERE abbreviation = ? ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlFindByStateAbbreviation, abbreviation);
		if(rows.next()) {
			selectedState = mapRowToState(rows);
		}
		return selectedState;
	}

	
	
	private State mapRowToState(SqlRowSet result) {
		State selectedState;
		selectedState = new State();
		selectedState.setStateAbbreviation(result.getString("abbreviation"));
		selectedState.setStateName(result.getString("name"));
		return selectedState;
	}

}
