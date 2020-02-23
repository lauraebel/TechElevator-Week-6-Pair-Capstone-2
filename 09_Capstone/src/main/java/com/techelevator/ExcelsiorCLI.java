package com.techelevator;

import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.category.CategoryDAO;
import com.techelevator.category.JDBCCategoryDAO;
import com.techelevator.city.CityDAO;
import com.techelevator.city.JDBCCityDAO;
import com.techelevator.reservation.JDBCReservationDAO;
import com.techelevator.reservation.ReservationDAO;
import com.techelevator.space.JDBCSpaceDAO;
import com.techelevator.space.Space;
import com.techelevator.space.SpaceDAO;
import com.techelevator.state.JDBCStateDAO;
import com.techelevator.state.StateDAO;
import com.techelevator.venue.JDBCVenueDAO;
import com.techelevator.venue.Venue;
import com.techelevator.venue.VenueDAO;

public class ExcelsiorCLI {

	private Menu menu;
	private CategoryDAO categoryDao;
	private CityDAO cityDao;
	private ReservationDAO reservationDao;
	private SpaceDAO spaceDao;
	private StateDAO stateDao;
	private VenueDAO venueDao;
	List<Venue> venues;
	private JDBCVenueDAO jdbcVenue;
	List<Space> spaces;

	public static void main(String[] args) {
		ExcelsiorCLI application = new ExcelsiorCLI();
		application.run();
	}

	public ExcelsiorCLI() {
		this.menu = new Menu();

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior-venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		categoryDao = new JDBCCategoryDAO(dataSource);
		cityDao = new JDBCCityDAO(dataSource);
		reservationDao = new JDBCReservationDAO(dataSource);
		spaceDao = new JDBCSpaceDAO(dataSource);
		stateDao = new JDBCStateDAO(dataSource);
		venueDao = new JDBCVenueDAO(dataSource);
	}

	public void run() {
		while (true) {
			String choice = menu.mainMenu();
			if (choice.equals("1")) { //Lists venues
				showAllVenues();
				
			} else if (choice.equalsIgnoreCase("Q")) { //Quits program
				System.exit(0);
			}
			while (true) {
				String venueChoice = menu.venueMenu();

					if (venueChoice.equalsIgnoreCase("R")) { //returns to main menu
						menu.mainMenu();
					} else if (venues.get(Integer.parseInt(venueChoice) - 1) != null) { //checks if choice is a valid venue id
						jdbcVenue.findVenueById(Integer.parseInt(venueChoice));
						showAllVenueDetails(); //TODO shows details of that venue - CURRENTLY NULL POINTER
					} 
				}
			}
			while (true) {
				String nextChoice = menu.whatNextMenu(); //After venue is selected and details are shown
				
					if (nextChoice.equals("1")) {
						//TODO List Venue Spaces: numbered, name, open month, close month, daily rate, and occupancy
					} else if(nextChoice.equals("2")) {
						//TODO search for reservation by reserved for
					} else if(nextChoice.equalsIgnoreCase("R")) { //returns to venue menu
						menu.venueMenu();
					}
			}
			while (true) {
				String spaceChoice = menu.spaceMenu();
				
				//TODO if statement checking if it is a valid choice
			}
			while (true) {
				String anotherChoice = menu.spaceNextVenue(); //after space is selected and details are shown
				
					if (anotherChoice.equals("1")) {
						//TODO reserve a space menu and confirmation - QUERY WILL NEED MADE IN JDBC
					} else if (anotherChoice.equalsIgnoreCase("R")) {
						menu.spaceMenu(); //returns to space menu
					}
		}
	}
}
	private void listVenues(List<Venue> venues) {
		for (Venue venue : venues) {
			System.out.println(venue.getVenueId() + ") " + venue.getVenueName());
		}
		System.out.println("R) Return to previous screen");
	}

	private void showAllVenues() {
	    venues = venueDao.getAllVenues();
		listVenues(venues);
	}

	private void listVenueDetails(List<Venue> venueDetails) {
		for (Venue venueDetailsList : venueDetails) {
			System.out.println("Venue Name: " + venueDetailsList.getVenueName() + " | Location: " + venueDetailsList.getCityName() + ", "
					+ venueDetailsList.getStateName() + " | Description: " + venueDetailsList.getVenueDescription());
		}
	}

	private void showAllVenueDetails() {
		venues = venueDao.getAllVenues();
		listVenueDetails(venues);
	}
	
	private void listSpaces(List<Space> spaces) {
		for (Space space : spaces) {
			System.out.println(space.getSpaceId() + ") " + space.getSpaceName() + " " + space.getOpenFrom() + " " + space.getOpenTo() + " " 
					+ space.getDailyRate() + " " + space.getMaxOccupancy());
		//TODO if statement that changes the open from and to ints to month strings
		}
	}
	
	private void showAllSpaces() {
		spaces = spaceDao.getAllSpaces();
		listSpaces(spaces);
	}
	
}
