package com.techelevator.city;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCityDAO implements CityDAO {

	private final JdbcTemplate jdbcTemplate;

	public JDBCCityDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(City newCity) {
		String sqlInsertCity = "INSERT INTO city(id, name, state_abbreviation) "
				+ "VALUES(?, ?, ?) ";
		newCity.setCityId(getNextCityId());
		jdbcTemplate.update(sqlInsertCity, newCity.getCityId(), newCity.getCityName(), newCity.getStateAbbreviation());	
	}

	@Override
	public void update(City city) {
		String sql = "UPDATE city SET name = ?, state_abbreviation = ? WHERE id = ? ";
		jdbcTemplate.update(sql, city.getCityName(), city.getStateAbbreviation(), city.getCityId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM city WHERE id = ? ";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public City findCityById(long id) {
		City selectedCity = null;
		String sqlFindCityById = "SELECT id, name, state_abbreviation " + "FROM city " + "WHERE id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindCityById, id);
		if (results.next()) {
			selectedCity = mapRowToCity(results);
		}
		return selectedCity;
	}
	
	private long getNextCityId() {
		SqlRowSet nextResResult = jdbcTemplate.queryForRowSet("SELECT nextval('city_id_seq')");
		if (nextResResult.next()) {
			return nextResResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new reservation");
		}
	}

	private City mapRowToCity(SqlRowSet result) {
		City selectedCity = new City();
		selectedCity.setCityId(result.getLong("id"));
		selectedCity.setCityName(result.getString("name"));
		selectedCity.setStateAbbreviation(result.getString("state_abbreviation"));
		return selectedCity;
	}

}
