package com.techelevator.reservation;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCReservationDAO implements ReservationDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void save(Reservation newReservation) {
		
	}

	@Override
	public Reservation findByReservationId(long id) {
		return null;
	}

	@Override
	public Reservation findReservationBySpaceId(long spaceId) {
		return null;
	}

	@Override
	public Reservation findReservationByStartDate(LocalDate startDate) {
		return null;
	}

	@Override
	public Reservation findReservationByEndDate(LocalDate endDate) {
		return null;
	}

	@Override
	public Reservation findReservationByReservedFor(String reservedFor) {
		return null;
	}

	@Override
	public void update(Reservation reservation) {
		
	}

	@Override
	public void delete(long id) {
		
	}

}
