package com.techelevator.reservation;


import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.state.State;

public class JDBCReservationDAO implements ReservationDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void createNewReservation(Reservation newReservation) {
		String sqlInsertReservation = "INSERT INTO reservation(reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) "
				+ "VALUES (?, ?, ?, ?, ?, ?) ";
		newReservation.setReservationId(getNextReservationId());
		jdbcTemplate.update(sqlInsertReservation, newReservation.getReservationId(), newReservation.getSpaceId(), newReservation.getNumberOfAttendees(), newReservation.getStartDate(), 
				newReservation.getEndDate(), newReservation.getReservedFor());
	}

	@Override
	public Reservation findByReservationId(long id) {
		Reservation selectedReservation = null;
		String sqlFindByResId = "SELECT reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for "
				+ "FROM reservation " + "WHERE reservation_id = ? ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlFindByResId, id);
		if(rows.next()) {
			selectedReservation = mapRowToReservation(rows);
		}
		return selectedReservation;
	}

	@Override
	public Reservation findReservationBySpaceId(long spaceId) {
		Reservation selectedReservation = null;
		String sqlFindBySpaceId = "SELECT reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for "
				+ "FROM reservation " + "WHERE space_id = ? ";
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlFindBySpaceId, spaceId);
		if(rows.next()) {
			selectedReservation = mapRowToReservation(rows);
		}
		return selectedReservation;
	}

	@Override
	public Reservation findReservationByReservedFor(String reservedFor) {
		Reservation selectedReservation = null;
		
		String sqlFindByReservedFor = "SELECT reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for "
				+ "FROM reservation "
				+ "WHERE reserved_for ";
//				"SELECT reservation.reservation_id AS reservation_id, reservation.space_id AS space_id, reservation.number_of_attendees AS number_of_attendees, reservation.start_date AS start_date, reservation.end_date AS end_date, reservation.reserved_for AS reserved_for, space.name AS space_name, venue.name AS venue_name "
//				+ "FROM reservation "
//				+ "JOIN space ON reservation.space_id = space.id "
//				+ "JOIN venue ON space.venue_id = venue.id "
//				+ "WHERE reserved_for = ? ";
		
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlFindByReservedFor, reservedFor);
		if(rows.next()) {
			selectedReservation = mapRowToReservation(rows);
		}
		return selectedReservation;
	}

	@Override
	public void update(Reservation reservation) {
		String sql = "UPDATE reservation SET space_id = ?, number_of_attendees = ?, start_date = ?, end_date = ?, reserved_for = ? WHERE reservation_id = ? ";
		jdbcTemplate.update(sql, reservation.getSpaceId(), reservation.getNumberOfAttendees(), 
				reservation.getStartDate(), reservation.getEndDate(), reservation.getReservedFor(), reservation.getReservationId());
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM reservation WHERE reservation_id = ? ";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public List<Reservation> getAllReservations() {
		List<Reservation> reservations = new ArrayList<Reservation>();
		SqlRowSet rows = jdbcTemplate.queryForRowSet("SELECT reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for FROM reservation ");
	
		while(rows.next()) {
			reservations.add(mapRowToReservation(rows));
		}
		return reservations;
	}
	
	private long getNextReservationId() {
		SqlRowSet nextResResult = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");
		if (nextResResult.next()) {
			return nextResResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new reservation");
		}
	}
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation selectedReservation = new Reservation();
		selectedReservation.setReservationId(results.getLong("reservation_id"));
		selectedReservation.setSpaceId(results.getLong("space_id"));
		selectedReservation.setNumberOfAttendees(results.getInt("number_of_attendees"));
		selectedReservation.setStartDate(results.getDate("start_date").toLocalDate());
		selectedReservation.setEndDate(results.getDate("end_date").toLocalDate());
		selectedReservation.setReservedFor(results.getString("reserved_for"));
//		selectedReservation.setSpaceName(results.getString("space_name"));
//		selectedReservation.setVenueName(results.getString("venue_name"));
		return selectedReservation;
	}



}
