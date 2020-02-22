package com.techelevator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.space.JDBCSpaceDAO;
import com.techelevator.space.Space;
import com.techelevator.space.SpaceDAO;

public class JDBCSpaceDAOIntegrationTest {

	private SpaceDAO dao;
	private JdbcTemplate jdbcTemplate;
	private static final String TEST_SPACE = "Fake Space";
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
		dao = new JDBCSpaceDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Test
	public void select_space_by_id() {
		Space space = getSpace(1, "Baltic Avenue", true, 8, 5, new BigDecimal(150.0), 900);
		dao.save(space);
		
		Space selectedSpace = dao.findSpaceById(space.getSpaceId());
		
		Assert.assertNotNull("returned null", selectedSpace);
		
		Assert.assertEquals("IDs Not equal", space.getSpaceId(), selectedSpace.getSpaceId());
		Assert.assertEquals("Names not equal", space.getSpaceName(), selectedSpace.getSpaceName());
		Assert.assertEquals("daily rates do not match", space.getDailyRate(), selectedSpace.getDailyRate());
	}
	
	@Test
	public void insert_new_space() {
		Space space = getSpace(1, "Baltic Avenue", true, 8, 5, new BigDecimal(400.0), 900);
		dao.save(space);
		
		Assert.assertNotEquals("Id not set", 0, space.getSpaceId());
		
		Space selectedSpace = dao.findSpaceById(space.getSpaceId());
		
		Assert.assertNotNull("null", selectedSpace);
		Assert.assertEquals("Space ID not as expected", space.getSpaceId(), selectedSpace.getSpaceId());
		Assert.assertEquals("Space name not as expected", space.getSpaceName(), selectedSpace.getSpaceName());
		Assert.assertEquals("Occupancy does not match", space.getMaxOccupancy(), selectedSpace.getMaxOccupancy());
	}
	
	@Test
	public void update_space() {
		Space space = getSpace(1, "Baltic Avenue", true, 8, 5, new BigDecimal(400.0), 900);
		dao.save(space);
		
		space.setSpaceName("updatedName");
		
		dao.update(space);
		
		Space updatedSpace = dao.findSpaceById(space.getSpaceId());
		
		Assert.assertNotNull("updatedSpace is null", updatedSpace);
		Assert.assertEquals("Updated Space IDs do not match", space.getSpaceId(), updatedSpace.getSpaceId());
		Assert.assertEquals("Updated Space names do not match", space.getSpaceName(), updatedSpace.getSpaceName());
		Assert.assertEquals("Open From months do not match", space.getOpenFrom(), updatedSpace.getOpenFrom());
	}
	
	@Test
	public void deleting_a_space() {
		Space space = getSpace(1, "Baltic Avenue", true, 8, 5, new BigDecimal(400.0), 900);
		dao.save(space);
		
		dao.delete(space.getSpaceId());
		
		Space deletedSpace = dao.findSpaceById(space.getSpaceId());
		Assert.assertNull(deletedSpace);
	}
	
	@Test
	public void shows_all_spaces() {			
		String sqlShowAllSpaces = "INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) "
				+ "VALUES (100, 15, ?, true, 7, 12, 400, 160) ";
		
		jdbcTemplate.update(sqlShowAllSpaces, TEST_SPACE);
				
		boolean showsSpaces = false;
		
		for(Space spaces : dao.getAllSpaces()) {
			String example = spaces.getSpaceName();
			if(example.equals("Fake Space")) {
				showsSpaces = true;
			}
		}
		Assert.assertTrue(showsSpaces);
	}
	
	private Space getSpace(long venueId, String name, boolean isAccessible, int openFrom, int openTo, BigDecimal dailyRate, int maxOccupancy) {
		Space selectedSpace = new Space();
		selectedSpace.setVenueId(5);
		selectedSpace.setSpaceName(name);
		selectedSpace.setAccessible(true);
		selectedSpace.setOpenFrom(3);
		selectedSpace.setOpenTo(10);
		selectedSpace.setDailyRate(new BigDecimal(150).setScale(1, RoundingMode.FLOOR));
		selectedSpace.setMaxOccupancy(100);
		return selectedSpace;
	}
	
}
