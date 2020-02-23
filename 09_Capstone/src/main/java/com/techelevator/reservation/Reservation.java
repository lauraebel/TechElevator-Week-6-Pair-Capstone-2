package com.techelevator.reservation;

import java.time.LocalDate;

public class Reservation {
	
	private long reservationId;
	private long spaceId;
	private int numberOfAttendees;
	private LocalDate startDate;
	private LocalDate endDate;
	private String reservedFor;
	private String venueName;
	private String spaceName;
	
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getSpaceName() {
		return spaceName;
	}
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	public long getReservationId() {
		return reservationId;
	}
	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
	public long getSpaceId() {
		return spaceId;
	}
	public void setSpaceId(long spaceId) {
		this.spaceId = spaceId;
	}
	public int getNumberOfAttendees() {
		return numberOfAttendees;
	}
	public void setNumberOfAttendees(int numberOfAttendees) {
		this.numberOfAttendees = numberOfAttendees;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getReservedFor() {
		return reservedFor;
	}
	public void setReservedFor(String reservedFor) {
		this.reservedFor = reservedFor;
	}
	
	

}
