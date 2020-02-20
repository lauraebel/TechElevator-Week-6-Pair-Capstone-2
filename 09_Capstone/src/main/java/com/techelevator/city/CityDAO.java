package com.techelevator.city;

import java.util.List;

public interface CityDAO {
	
	List<City> getAllCities();
	
	public void save(City newCity);
		
	public City findCityById(long id);
		
	public List<City> findCityByStateAbbreviation(String stateAbbreviation);
	
	public void update(City city);
	
	public void delete(long id);
	
}
