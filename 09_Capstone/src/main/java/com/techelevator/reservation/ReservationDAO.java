package com.techelevator.reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	List<Reservation> getAllReservations();

	public void save(Reservation newReservation);
	
	public Reservation findByReservationId(long id);
	
	public Reservation findReservationBySpaceId(long spaceId);
	
	public Reservation findReservationByStartDate(LocalDate startDate);
	
	public Reservation findReservationByEndDate(LocalDate endDate);
	
	public Reservation findReservationByReservedFor(String reservedFor);
	
	public void update(Reservation reservation);
	
	public void delete(long id);
}
