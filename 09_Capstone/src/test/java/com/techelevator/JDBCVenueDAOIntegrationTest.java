package com.techelevator;

import java.sql.SQLException;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.venue.JDBCVenueDAO;
import com.techelevator.venue.Venue;
import com.techelevator.venue.VenueDAO;

public class JDBCVenueDAOIntegrationTest {
	
	private VenueDAO dao;
	private JdbcTemplate jdbcTemplate;
	private static final String TEST_VENUE = "Fake Venue";
	private static SingleConnectionDataSource dataSource;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior-venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Before
	public void setup() {
		dao = new JDBCVenueDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Test
	public void selecting_by_venue_id() {
		Venue venue = getVenue("name", 4, "description");
		dao.save(venue);
		
		Venue selectedVenue = dao.findVenueById(venue.getVenueId());
		
		Assert.assertNotNull(selectedVenue);
		Assert.assertEquals(venue.getVenueId(), selectedVenue.getVenueId());
	}
	
	@Test
	public void inserting_new_venue() {
		Venue venue = getVenue("name", 4, "description");
		dao.save(venue);
		
		Venue newVenue = dao.findVenueById(venue.getVenueId());
		
		Assert.assertNotNull(newVenue);
		Assert.assertEquals(venue.getCityName(), newVenue.getCityName());
		Assert.assertEquals(venue.getVenueDescription(), newVenue.getVenueDescription());
	}

	@Test
	public void updating_a_venue_name() {
		Venue venue = getVenue("name", 4, "description");
		dao.save(venue);
		
		venue.setVenueName("updatedName");
		dao.update(venue);
		
		Venue updatedVenue = dao.findVenueById(venue.getVenueId());
		
		Assert.assertNotNull(updatedVenue);
		Assert.assertEquals(venue.getVenueName(), updatedVenue.getVenueName());
	}

	
	@Test
	public void deleting_a_venue() {
		Venue venue = getVenue("name", 4, "description");
		dao.save(venue);
		
		dao.delete(venue.getVenueId());
		
		Venue deletedVenue = dao.findVenueById(venue.getVenueId());
		
		Assert.assertNull(deletedVenue);
	}
	
	@Test
	public void shows_all_venues() {
		String sqlShowsAllVenues = "INSERT INTO venue (id, name, city_id, description) "
				+ "VALUES (17, ?, 3, 'description') ";
		
		jdbcTemplate.update(sqlShowsAllVenues, TEST_VENUE);
		
		boolean showsVenues = false;
		
		for(Venue venues : dao.getAllVenues()) {
			String example = venues.getVenueName();
			if(example.equals("Fake Venue")) {
				showsVenues = true;
			}
		}
		Assert.assertTrue(showsVenues);
	}
 	
	private Venue getVenue(String name, long cityId, String description) {
		Venue selectedVenue = new Venue();
		selectedVenue.setVenueName("venueName");
		selectedVenue.setCityName("cityName");
		selectedVenue.setVenueDescription("venueDescription");
		return selectedVenue;
	}
}
