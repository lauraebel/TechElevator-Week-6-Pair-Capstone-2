package com.techelevator.space;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSpaceDAO implements SpaceDAO{
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCSpaceDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Space newSpace) {
		String sqlInsertSpace = "INSERT INTO space(id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
		newSpace.setSpaceId(getNextSpaceId());
		jdbcTemplate.update(sqlInsertSpace, newSpace.getSpaceId(), newSpace.getVenueId(), newSpace.getSpaceName(), 
				newSpace.isAccessible(), newSpace.getOpenFrom(), newSpace.getOpenTo(), newSpace.getDailyRate(), newSpace.getMaxOccupancy());
	}

	@Override
	public void update(Space space) {
		String sql = "UPDATE space SET venue_id = ?, name = ?, is_accessible = ?, open_from = ?, open_to = ?, daily_rate = ?, max_occupancy = ? WHERE id = ? ";
		jdbcTemplate.update(sql, space.getVenueId(), space.getSpaceName(), space.isAccessible(), 
				space.getOpenFrom(), space.getOpenTo(), space.getDailyRate(), space.getMaxOccupancy());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM space WHERE id = ? ";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Space> getAllSpaces() {
		List<Space> spaces = new ArrayList<Space>();
		SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy");
		
		if(rows.next()) {
			spaces.add(mapRowToSpace(rows));
		}
		return spaces;
	}

	@Override
	public Space findSpaceById(long id) {
		Space selectedSpace = null;
		String sqlFindSpaceById = "SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy " + "FROM space "
		+ "WHERE id = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlFindSpaceById, id);
		
		if (result.next()) {
			selectedSpace = mapRowToSpace(result);
		}
		return selectedSpace;
	}

	@Override
	public List<Space> findSpaceByVenueId(long venueId) {
		List<Space> spaces = new ArrayList<>();
		String sqlFindSpaceByVenueId = "SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy " + "FROM space "
		+ "WHERE venue_id = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlFindSpaceByVenueId, venueId);
		
		while (result.next()) {
			Space selectedSpace = mapRowToSpace(result);
			spaces.add(selectedSpace);
		}
		return spaces;
	}

	@Override
	public List<Space> findSpaceByAccessibility(boolean isAccessible) {
		List<Space> spaces = new ArrayList<>();
		String sqlFindSpaceByAccessibility = "SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy " + "FROM space "
		+ "WHERE is_accessible = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlFindSpaceByAccessibility, isAccessible);
		
		while(result.next()) {
			Space selectedSpace = mapRowToSpace(result);
			spaces.add(selectedSpace);
		}
		return spaces;
	}

	@Override
	public List<Space> findSpaceByOpenMonth(int openFrom) {
		List<Space> spaces = new ArrayList<>();
		String sqlFindSpaceByOpenMonth = "SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy " + "FROM space "
		+ "WHERE open_from = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlFindSpaceByOpenMonth, openFrom);
		
		while(result.next()) {
			Space selectedSpace = mapRowToSpace(result);
			spaces.add(selectedSpace);
		}
		return spaces;
	}

	@Override
	public List<Space> findSpaceByCloseMonth(int openTo) {
		List<Space> spaces = new ArrayList<>();
		String sqlFindSpaceByCloseMonth = "SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy " + "FROM space "
		+ "WHERE open_to = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlFindSpaceByCloseMonth, openTo);
		
		while(result.next()) {
			Space selectedSpace = mapRowToSpace(result);
			spaces.add(selectedSpace);
		}
		return spaces;
	}
	@Override
	public List<Space> findSpaceByRate(double dailyRate) {
		List<Space> spaces = new ArrayList<>();
		String sqlFindSpaceByRate = "SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy " + "FROM space "
		+ "WHERE daily_rate = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlFindSpaceByRate, dailyRate);
		
		while(result.next()) {
			Space selectedSpace = mapRowToSpace(result);
			spaces.add(selectedSpace);
		}
		return spaces;
	}

	@Override
	public List<Space> findSpaceByOccupancy(int maxOccupancy) {
		List<Space> spaces = new ArrayList<>();
		String sqlFindSpaceByOccupancy = "SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy " + "FROM space "
		+ "WHERE max_occupancy = ? ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlFindSpaceByOccupancy, maxOccupancy);
		
		while(result.next()) {
			Space selectedSpace = mapRowToSpace(result);
			spaces.add(selectedSpace);
		}
		return spaces;
	}

	private long getNextSpaceId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_space_id')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new space");
		}
	}
	
	private Space mapRowToSpace(SqlRowSet result) {
		Space selectedSpace = new Space();
		selectedSpace.setSpaceId(result.getLong("id"));
		selectedSpace.setVenueId(result.getLong("venue_id"));
		selectedSpace.setSpaceName(result.getString("name"));
		selectedSpace.setAccessible(result.getBoolean("is_accessible"));
		selectedSpace.setOpenFrom(result.getInt("open_from"));
		selectedSpace.setOpenTo(result.getInt("open_to"));
		selectedSpace.setDailyRate(result.getDouble("daily_rate"));
		selectedSpace.setMaxOccupancy(result.getInt("max_occupancy"));
		return selectedSpace;
	}
	
}
