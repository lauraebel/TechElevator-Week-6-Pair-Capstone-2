package com.techelevator;

import java.util.List;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.city.JDBCCityDAO;
import com.techelevator.venue.JDBCVenueDAO;
import com.techelevator.venue.Venue;

public class JDBCVenueDAOIntegrationTest {
	
	private JDBCVenueDAO venueDao;
	private int venueId;
	private JdbcTemplate jdbcTemplate;

	/*
	 * Using this particular implementation of DataSource so that every database
	 * interaction is part of the same database session and hence the same database
	 * transaction
	 */
	private static SingleConnectionDataSource dataSource;

	/*
	 * Before any tests are run, this method initializes the datasource for testing.
	 */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior-venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/*
		 * The following line disables autocommit for connections returned by this
		 * DataSource. This allows us to rollback any changes after each test
		 */
		dataSource.setAutoCommit(false);
	}

	/*
	 * After all tests have finished running, this method will close the DataSource
	 */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/*
	 * After each test, we rollback any changes that were made to the database so
	 * that everything is clean for the next test
	 */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/*
	 * This method provides access to the DataSource for subclasses so that they can
	 * instantiate a DAO for testing
	 */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "INSERT INTO venue(id, name, city_id, description) VALUES (?, 'name', ?, 'description')";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql);	
		venueDao = new JDBCVenueDAO(dataSource);
	}
	

	@Test
	public void show_all_venues() {			
		List<Venue> results = venueDao.getAllVenues();

		Assert.assertNotNull(results);
		Assert.assertTrue(results.size() >= 1);
	}
	
	@Test
	public void creating_new_venue() {
		Venue venue = new Venue();
		venue.setVenueName("Name");
		venue.setCityId(2);
		venue.setVenueDescription("description");
		
		venueDao.save(venue);
		
		Venue newVenue = venueDao.findVenueById(venue.getVenueId());
		Assert.assertNotEquals(0, newVenue.getVenueId());
		Assert.assertNotNull(venue);
		

	}
}
	
	

