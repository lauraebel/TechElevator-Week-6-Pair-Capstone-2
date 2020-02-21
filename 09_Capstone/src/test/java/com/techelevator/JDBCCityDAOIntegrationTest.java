package com.techelevator;

import java.sql.SQLException;

import org.junit.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.city.City;
import com.techelevator.city.CityDAO;
import com.techelevator.city.JDBCCityDAO;

public class JDBCCityDAOIntegrationTest {
	
	private CityDAO dao;
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
		dao = new JDBCCityDAO(dataSource);

	}
	
	@Test
	public void select_city_by_id() {
		City city = getCity("name", "state");
		dao.save(city);
		
		City newCity = dao.findCityById(city.getCityId());
		
		Assert.assertNotNull("city is null", newCity);
		
		Assert.assertEquals("city IDs not equal", city.getCityId(), newCity.getCityId());
		Assert.assertEquals("city names not equal", city.getCityName(), newCity.getCityName());
		Assert.assertEquals("state abbreviations not equal", city.getStateAbbreviation(), newCity.getStateAbbreviation());
	}
	
	@Test
	public void insert_new_city() {
		City city = getCity("name", "state");
		dao.save(city);
		
		Assert.assertNotEquals("ID not set", 0, city.getCityId());
	
		City selectedCity = dao.findCityById(city.getCityId());
	
		Assert.assertNotNull("new city is null", selectedCity);
		Assert.assertEquals("city IDs are not equal", city.getCityId(), selectedCity.getCityId());
		Assert.assertEquals("city names are not equal", city.getCityName(), selectedCity.getCityName());
		Assert.assertEquals("city state abbreviations are not equal", city.getStateAbbreviation(), selectedCity.getStateAbbreviation());
	}
	
	@Test
	public void update_city_name() {
		City city = getCity("name", "state");
		dao.save(city);
		
		city.setCityName("updated name");
		dao.update(city);
		
		City updatedCity = dao.findCityById(city.getCityId());
		
		Assert.assertNotNull("updatedCity is null", updatedCity);
		Assert.assertEquals("updated names do not match", city.getCityName(), updatedCity.getCityName());
		Assert.assertEquals("updated IDs do not match", city.getCityId(), updatedCity.getCityId());
	}
	
	@Test
	public void delete_city() {
		City city = getCity("name", "state");
		dao.save(city);
		
		dao.delete(city.getCityId());
		
		City deletedCity = dao.findCityById(city.getCityId());
		Assert.assertNull(deletedCity);
	}

	
	private City getCity(String name, String stateAbbreviation) {
		City selectedCity = new City();
		selectedCity.setCityName("name");
		selectedCity.setStateAbbreviation("OH");
		return selectedCity;
	}
}
