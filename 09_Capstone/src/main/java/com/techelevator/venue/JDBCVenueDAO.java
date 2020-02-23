package com.techelevator.venue;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCVenueDAO implements VenueDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCVenueDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Venue> getAllVenues() {
		List<Venue> venues = new LinkedList<Venue>();
		String selectAllVenues = "SELECT venue.id AS venue_id, venue.name AS venue_name, venue.city_id AS city_id, venue.description AS venue_description, city.name AS city_name, state.abbreviation AS state_abbreviation "
				+ "FROM venue "
				+ "JOIN city ON city_id = city.id "
				+ "JOIN state ON city.state_abbreviation = state.abbreviation ";
		
		SqlRowSet result = jdbcTemplate.queryForRowSet(selectAllVenues);
		
		while(result.next()) {
			venues.add(mapRowToVenue(result));
		}
		return venues;
	}
	
	@Override
	public void save(Venue newVenue) {
		String sqlInsertVenue = "INSERT INTO venue(id, name, city_id, description) VALUES(?, ?, ?, ?)";

		newVenue.setVenueId(getNextVenueId());
		
		jdbcTemplate.update(sqlInsertVenue, newVenue.getVenueId(), newVenue.getVenueName(), newVenue.getCityName(), newVenue.getVenueDescription());
	}
	
	@Override
	public void update(Venue venue) {
		String sql = "UPDATE venue SET name = ?, city_id = ?, description = ? WHERE id = ?";
		jdbcTemplate.update(sql, venue.getVenueName(), venue.getCityName(), venue.getVenueDescription(), venue.getVenueId());	
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM venue WHERE id = ?";
		jdbcTemplate.update(sql, id);
		
	}
	@Override
	public Venue findVenueById(long id) {
		Venue thevenue = null;
		String sqlFindVenueById = "SELECT id, name, city_id, description FROM venue WHERE id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindVenueById, id);
		if (results.next()) {
			thevenue = mapRowToVenue(results);
		}
		return thevenue;
	}
	
	private Venue mapRowToVenue(SqlRowSet result) {
		Venue venues = new Venue();
		venues.setVenueId(result.getLong("venue_id"));
		venues.setVenueName(result.getString("venue_name"));
		venues.setCityId(result.getLong("city_id"));
		venues.setCityName(result.getString("city_name"));
		venues.setVenueDescription(result.getString("venue_description"));
		venues.setStateName(result.getString("state_abbreviation"));
		return venues;
	}
	
	private long getNextVenueId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('venue_id_seq')");

		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new venue");
		}
	}

}
