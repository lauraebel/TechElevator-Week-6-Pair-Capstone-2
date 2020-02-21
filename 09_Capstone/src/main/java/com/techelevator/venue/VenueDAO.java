package com.techelevator.venue;

import java.util.List;

public interface VenueDAO {
	
	public void save(Venue newVenue);
	
	public List<Venue> getAllVenues();
	
	public Venue findVenueById(long id);
		
	public List<Venue> findVenueByCityId(long cityId);
	
	public void update(Venue venue);
	
	public void delete(long id);

}
