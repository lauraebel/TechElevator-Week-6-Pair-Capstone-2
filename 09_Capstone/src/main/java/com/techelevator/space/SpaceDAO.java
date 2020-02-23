package com.techelevator.space;

import java.time.LocalDate;
import java.util.List;

public interface SpaceDAO {
	
	public void save(Space newSpace);
	
	public List<Space> getAllSpaces();
	
	public Space findSpaceById(long id);
	
	public List<Space> getAllSpacesInVenue(long venueId);
		
	public void update(Space space);
	
	public void delete(long id);
	
	public List<Space> getAvailableSpaces(LocalDate startDate, LocalDate endDate, int attendance);
}
