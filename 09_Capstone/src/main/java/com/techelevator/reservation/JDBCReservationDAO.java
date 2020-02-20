package com.techelevator.reservation;

import java.time.LocalDate;

public class JDBCReservationDAO implements ReservationDAO {

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
