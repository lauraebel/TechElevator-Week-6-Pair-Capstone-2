package com.techelevator.reservation;

import java.sql.Date;
import java.util.List;

public interface ReservationDAO {
	
	List<Reservation> getAllReservations();

	public void save(Reservation newReservation);
	
	public Reservation findByReservationId(long id);
	
	public Reservation findReservationBySpaceId(long spaceId);
		
	public Reservation findReservationByReservedFor(String reservedFor);
	
	public void update(Reservation reservation);
	
	public void delete(long id);
	
	public Reservation findReservationByStartDate(Date startDate);
	
	public Reservation findReservationByEndDate(Date endDate);
}
