package com.techelevator.city;

import java.util.List;

public interface CityDAO {
		
	public void save(City newCity);
		
	public City findCityById(long id);
			
	public void update(City city);
	
	public void delete(long id);
	
}
