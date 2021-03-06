package com.techelevator.state;

import java.util.List;

public interface StateDAO {

	public void save(State newState);
	
	public List<State> getAllStates();
		
	public State findStateByAbbreviation(String abbreviation);
	
	public void update(State state);
	
	public void delete(String abbreviation);
}
