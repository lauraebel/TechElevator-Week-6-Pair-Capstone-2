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
				space.getOpenFrom(), space.getOpenTo(), space.getDailyRate(), space.getMaxOccupancy(), space.getSpaceId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM space WHERE id = ? ";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Space> getAllSpaces() {
		List<Space> spaces = new ArrayList<Space>();
		SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate::decimal, max_occupancy FROM space");
		
		while(rows.next()) {
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

	private long getNextSpaceId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('space_id_seq')");
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
		selectedSpace.setDailyRate(result.getBigDecimal("daily_rate"));
		selectedSpace.setMaxOccupancy(result.getInt("max_occupancy"));
		return selectedSpace;
	}
	
}
