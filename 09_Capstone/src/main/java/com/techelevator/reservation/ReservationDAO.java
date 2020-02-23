package com.techelevator.reservation;

import java.util.List;

public interface ReservationDAO {
	
	public void createNewReservation(Reservation newReservation);
	
	public Reservation findByReservationId(long id);

	public Reservation findReservationBySpaceId(long spaceId);
	
	public Reservation findReservationByReservedFor(String reservedFor);
	
	public void update(Reservation reservation);
	
	public void delete(long id);
	
	List<Reservation> getAllReservations();
}
