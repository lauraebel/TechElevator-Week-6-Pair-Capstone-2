package com.techelevator.city;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.venue.Venue;

public class JDBCCityDAO implements CityDAO {

	private final JdbcTemplate jdbcTemplate;

	public JDBCCityDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<City> getAllCities() {
		List<City> cities = new ArrayList<City>();
		String selectAllCities = "SELECT id, name, state_abbreviation FROM city";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(selectAllCities);

		while (rows.next()) {
			cities.add(mapRowToCity(rows));
		}
		return cities;
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
		String sql = "UPDATE city SET id = ?, name = ?, state_abbreviation = ? WHERE id = ? ";
		jdbcTemplate.update(sql, city.getCityId(), city.getCityName(), city.getStateAbbreviation());
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

	@Override
	public List<City> findCityByStateAbbreviation(String stateAbbreviation) {
		List<City> cities = new ArrayList<>();
		String sqlFindCityByStateAbbreviation = "SELECT id, name, state_abbreviation " + "FROM city "
				+ "WHERE state_abbreviation = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindCityByStateAbbreviation, stateAbbreviation);
		if (results.next()) {
			City selectedCity = mapRowToCity(results);
			cities.add(selectedCity);
		}
		return cities;
	}

	private City mapRowToCity(SqlRowSet result) {
		City selectedCity = new City();
		selectedCity.setCityId(result.getLong("id"));
		selectedCity.setCityName(result.getString("name"));
		selectedCity.setStateAbbreviation(result.getString("state_abbreviation"));
		return selectedCity;
	}

	private long getNextCityId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_city_id')");

		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new city");
		}
	}

}
