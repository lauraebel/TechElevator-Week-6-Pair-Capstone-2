package com.techelevator.city;

import java.util.List;

public interface CityDAO {
	
	public void save(City newCity);
	
	public List<City> getAllCities();
	
	public List<City> findCityById(long id);
	
	public List<City> findCityByName(String name);
	
	public List<City> findCityByAbbreviation(String abbreviation);
	
	public void update(City city);
	
	public void delete(long id);
	
}
