package com.techelevator.space;

import java.util.List;

public interface SpaceDAO {
	
	public void save(Space newSpace);
	
	public List<Space> getAllSpaces();
	
	public Space findSpaceById(long id);
	
	public List<Space> findSpaceByVenueId(long venueId);
		
	public List<Space> findSpaceByAccessibility(boolean isAccessible);
	
	public List<Space> findSpaceByOpenMonth(int openFrom);
	
	public List<Space> findSpaceByCloseMonth(int openTo);
	
	public List<Space> findSpaceByRate(double dailyRate);
	
	public List<Space> findSpaceByOccupancy(int maxOccupancy);
	
	public void update(Space space);
	
	public void delete(long id);
}
