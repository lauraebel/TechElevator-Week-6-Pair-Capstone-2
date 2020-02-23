package com.techelevator;

import java.util.Scanner;

public class Menu {
	
	private Scanner in = new Scanner(System.in);
	
	
	public String mainMenu() {
		String choice = null;
		
		System.out.println("What would you like to do?\n(1) List Venues\n(Q) Quit");

			choice = in.nextLine();
		return choice;
	}
	
	public String venueMenu() {
		String choiceVenue = null;
		
		System.out.println("Which venue would you like to view?");

			choiceVenue = in.nextLine();

		return choiceVenue;
	}
	
	public String spaceMenu() {
		String spaceChoice = null;
		
		spaceChoice = in.nextLine();
		
		return spaceChoice;
	}
	
	public String whatNextMenu() {
		String choiceNext = null;
		
		System.out.println("What would you like to do next?\n(1) View Spaces\n(2) Search for a Reservation\n(R) Return to previous screen");
		
			choiceNext = in.nextLine();
			
		return choiceNext;
	}
	
	public String spaceNextVenue() {
		String choiceSpace = null;
		
		System.out.println("What would you like to do next?\n(1) Reserve a Space\n(R) Return to previous screen");
		
			choiceSpace = in.nextLine();
		
		return choiceSpace;
	}
	
	public String searchForReservation() {
		String reservationChoice = null;
		
		System.out.println("What is the name of the reservation you are looking for?");
		
		reservationChoice = in.nextLine();
		
		return reservationChoice;
	}
	

	public String startDateOfReservation() {
		String startDateChosen = null;
		
		System.out.println("On what date will the reservation start? (MM/DD/YYYY)");
		
		startDateChosen = in.nextLine();
		

		
		return startDateChosen;
	}
	
	public String endDateOfReservation() {
		String endDateChosen = null;
		
		System.out.println("On what date will the reservation end? (MM/DD/YYYY)");
		
		endDateChosen = in.nextLine();
		
		
		return endDateChosen;
	}
	
	public int howManyPeople() {
		int resAttendees = 0;
		
		System.out.println("How many people will be in attendance?");
		
		resAttendees = in.nextInt();
		
		return resAttendees;
	}
	
	public void availableSpacesHeading() {
		System.out.println("***The following spaces are available based on your needs:");
	}
	
	
	
	
}


	
	
	


	
	
	
	
	
	

	



