package com.techelevator.venue;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCVenueDAO implements VenueDAO{
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCVenueDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Venue> getAllVenues() {
		List<Venue> venues = new ArrayList<Venue>();
		String selectAllVenues = "SELECT * FROM venue";
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
		
		jdbcTemplate.update(sqlInsertVenue, newVenue.getVenueId(), newVenue.getVenueName(), newVenue.getCityId(), newVenue.getVenueDescription());
	}
	
	@Override
	public void update(Venue venue) {
		
	}

	@Override
	public void delete(long id) {
		
	}
	@Override
	public Venue findVenueById(long id) {
		return null;
	}

	@Override
	public Venue findVenueByName(String name) {
		return null;
	}

	@Override
	public Venue findVenueByCityId(long cityId) {
		return null;
	}
	
	private Venue mapRowToVenue(SqlRowSet result) {
		Venue venues = new Venue();
		venues.setVenueId(result.getLong("venue_id"));
		venues.setVenueName(result.getString("name"));
		venues.setCityId(result.getLong("city_id"));
		venues.setVenueDescription(result.getString("description"));
		return venues;
	}
	
	private long getNextVenueId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_venue_id')");

		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new venue");
		}
	}

}
