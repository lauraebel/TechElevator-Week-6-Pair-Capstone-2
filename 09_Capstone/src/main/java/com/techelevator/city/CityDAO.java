package com.techelevator.city;

public interface CityDAO {
		
	public void save(City newCity);
		
	public City findCityById(long id);
			
	public void update(City city);
	
	public void delete(long id);
	
}
