package com.techelevator;

import java.sql.SQLException;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.state.JDBCStateDAO;
import com.techelevator.state.State;
import com.techelevator.state.StateDAO;

public class JDBCStateDAOIntegrationTest {
	
	private StateDAO dao;
	private JdbcTemplate jdbcTemplate;
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
		dao = new JDBCStateDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void selecting_state_by_abbreviation() {
		State state = getState("TX", "Texas");
		dao.save(state);
		
		State selectedState = dao.findStateByAbbreviation(state.getStateAbbreviation());
		
		Assert.assertNotNull("state is null", selectedState);
		Assert.assertEquals("abbreviations not equal", state.getStateAbbreviation(), selectedState.getStateAbbreviation());
	}
	
	@Test
	public void updating_a_state() {
		State state = getState("TX", "texas");
		dao.save(state);
				
		state.setStateName("updatedName");
		dao.update(state);
		
		State updatedState = dao.findStateByAbbreviation(state.getStateAbbreviation());
		
		Assert.assertNotNull("updatedState is null", updatedState);
		Assert.assertEquals("updated names not equal", state.getStateName(), updatedState.getStateName());
	}
	
	@Test 
	public void insertint_new_state() {
		State newState = getState("FL", "Florida");
		dao.save(newState);
		
		State selectedState = dao.findStateByAbbreviation(newState.getStateAbbreviation());
		
		Assert.assertNotNull("newState is null", selectedState);
		Assert.assertEquals(newState.getStateName(), selectedState.getStateName());
		Assert.assertEquals(newState.getStateAbbreviation(), selectedState.getStateAbbreviation());
	}
	
	@Test
	public void deleting_a_state() {
		State newState = getState("FL", "Florida");
		dao.save(newState);
		
		dao.delete(newState.getStateAbbreviation());
		
		State deletedState = dao.findStateByAbbreviation(newState.getStateAbbreviation());
		
		Assert.assertNull(deletedState);
		
	}
	
	private State getState(String abbreviation, String name) {
		State selectedState = new State();
		selectedState.setStateAbbreviation("TX");
		selectedState.setStateName("Texas");
		return selectedState;
	}

}
