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
		
			String choice = menu.mainMenu();
			
			if (choice.equals("1")) { //Lists venues
				venues = venueDao.getAllVenues();
				listVenues(venues);
				
				String venueChoice = menu.venueMenu();
				
				if (venueChoice.equalsIgnoreCase("R")) { //returns to main menu
					System.out.flush();
					menu.mainMenu();
					
				} else if (venues.get(Integer.parseInt(venueChoice) - 1) != null) { //checks if choice is a valid venue id
					Venue chosenVenue = venues.get(Integer.parseInt(venueChoice) -1);
					listVenueDetails(chosenVenue); 
					System.out.println();
				} 
				
				String nextChoice = menu.whatNextMenu(); //After venue is selected and details are shown - "What would you like to do next?\n(1) View Spaces\n(2) Search for Reservation\n(R) Return to previous screen")
				
				if (nextChoice.equals("1")) {
					spaces = spaceDao.getAllSpaces();
					listSpaces(spaces);
					
					String anotherChoice = menu.spaceNextVenue();//"What would you like to do next?\n(1) Reserve a Space\n(R) Return to previous screen")
					
					if (anotherChoice.equals("1")) {
						
						String spaceChoice = menu.spaceMenu();
//						if (venues.get(Integer.parseInt(venueChoice) - 1) != null) { //checks if choice is a valid space id
//							jdbcVenue.findSpaceById(Integer.parseInt(spaceChoice));
//							showAllVenueDetails();
						//TODO reserve a space menu and confirmation - QUERY WILL NEED MADE IN JDBC
						
					} else if (anotherChoice.equalsIgnoreCase("R")) {
						//returns to venue details 
					}
					

				} if(nextChoice.equals("2")) {
					//TODO search for reservation by reserved for
					
				} else if(nextChoice.equalsIgnoreCase("R")) { //returns to venue menu
					menu.venueMenu();
				}
				
			} else if (choice.equalsIgnoreCase("Q")) { //Quits program
				System.exit(0);
			}
	}

	private void listVenues(List<Venue> venues) {
		for (Venue venue : venues) {
			System.out.println(venue.getVenueId() + ") " + venue.getVenueName());
		}
		System.out.println("R) Return to previous screen");
	}

	private void listVenueDetails(Venue venue) {
			System.out.println("Venue Name: " + venue.getVenueName());
			System.out.println("Location: " + venue.getCityName() + ", " + venue.getStateName());
			System.out.println("Category(ies): sorry - couldn't figure this out but it definitely has some");
			System.out.println("Description: " + venue.getVenueDescription());
	}
	
	private void listSpaces(List<Space> spaces) {
		String[] month = spaces.get(0).getMonths();
		System.out.printf("%-15s %-43s %-15s %-11s %-13s %-15s\n", "ID Number", "Name", "Open Mo.", "Close Mo.", "Daily Rate", "Maximum Occupancy");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
		for (Space space : spaces) {
			System.out.printf("%-15d %-45s %-15s %-10s $%-15.2f %-40d\n", space.getSpaceId(), space.getSpaceName(), month[space.getOpenFrom()], month[space.getOpenTo()],
					 space.getDailyRate(), space.getMaxOccupancy());
		
		}
		System.out.println();
	}
	
}
