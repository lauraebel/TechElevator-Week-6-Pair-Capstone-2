package com.techelevator;

import java.util.List;
import java.util.Scanner;

import com.techelevator.category.CategoryDAO;
import com.techelevator.city.CityDAO;
import com.techelevator.reservation.ReservationDAO;
import com.techelevator.space.SpaceDAO;
import com.techelevator.state.StateDAO;
import com.techelevator.venue.Venue;
import com.techelevator.venue.VenueDAO;

public class Menu {
	
	private CategoryDAO categoryDao;
	private CityDAO cityDao;
	private ReservationDAO reservationDao;
	private SpaceDAO spaceDao;
	private StateDAO stateDao;
	private VenueDAO venueDao;
	List<Venue> venues;
	
	private Scanner in = new Scanner(System.in);
		
	public void displayMessage(String message) {
		System.out.println(message);
	}
	
	public int mainMenu(List<Venue> venues) {
		int choice = 0;
		boolean loop;
		
		do {
			System.out.println( "(1) List Venues\n(Q) Quit");

			loop = false;
			choice = in.nextInt();
			in.nextLine();
			
		} while (loop);
			return choice;
		}
		
	}

//	public int mainMenu(Map<String, Fridge> inventory) {
//	boolean loop;
//	int choice = 0;
//	do {
//	System.out.println("(1) Display Catering Items\n(2) Order\n(3) Quit");
//	try {
//		loop = false;
//		choice = in.nextInt();
//		
//	} catch (InputMismatchException e) {
//		System.out.println("Error: Invalid option. Please choose again.");
//		loop = true;
//	} in.nextLine();
//
//	} while (loop);
//		return choice;
//}



