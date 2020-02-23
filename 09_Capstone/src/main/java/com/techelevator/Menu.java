package com.techelevator;

import java.util.List;
import java.util.Scanner;

import com.techelevator.venue.Venue;
import com.techelevator.venue.VenueDAO;

public class Menu {
	
	private Scanner in = new Scanner(System.in);
	private List<Venue> venues;
	private VenueDAO venueDao;

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
		
		System.out.println("What would you like to do next?\n(1) View Spaces\n(2) Search for Reservation\n(R) Return to previous screen");
		
			choiceNext = in.nextLine();
			
		return choiceNext;
	}
	
	public String spaceNextVenue() {
		String choiceSpace = null;
		
		System.out.println("What would you like to do next?\n(1) Reserve a Space\n(R) Return to previous screen");
		
			choiceSpace = in.nextLine();
		
		return choiceSpace;
	}
	
}


	
	
	


	
	
	
	
	
	

	



