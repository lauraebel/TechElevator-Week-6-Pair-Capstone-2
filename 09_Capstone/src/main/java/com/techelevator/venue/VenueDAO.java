package com.techelevator.venue;

import java.util.List;

public interface VenueDAO {
	
	public void save(Venue newVenue);
	
	public List<Venue> getAllVenues();
	
	public List<Venue> findVenueById(long id);
	
	public List<Venue> findVenueByName(String name);
	
	public List<Venue> findVenueByCityId(long cityId);
	
	public void update(Venue venue);
	
	public void delete(long id);

}
