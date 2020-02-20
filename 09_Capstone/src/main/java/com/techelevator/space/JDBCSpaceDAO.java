package com.techelevator.space;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCSpaceDAO implements SpaceDAO{
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCSpaceDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Space newSpace) {
		
	}

	@Override
	public void update(Space space) {
		
	}

	@Override
	public void delete(long id) {
		
	}

	@Override
	public List<Space> getAllSpaces() {
		return null;
	}

	@Override
	public List<Space> findSpaceById(long id) {
		return null;
	}

	@Override
	public List<Space> findSpaceByVenueId(long venueId) {
		return null;
	}

	@Override
	public List<Space> findSpaceByName(String name) {
		return null;
	}

	@Override
	public List<Space> findSpaceByAccessibility(boolean isAccessible) {
		return null;
	}

	@Override
	public List<Space> findSpaceByOpenMonth(int openFrom) {
		return null;
	}

	@Override
	public List<Space> findSpaceByCloseMonth(int openTo) {
		return null;
	}

	@Override
	public List<Space> findSpaceByRate(double dailyRate) {
		return null;
	}

	@Override
	public List<Space> findSpaceByOccupancy(int maxOccupancy) {
		return null;
	}

}
