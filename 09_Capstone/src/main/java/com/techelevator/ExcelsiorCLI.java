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
	
	public static void main(String[] args) {
		ExcelsiorCLI application = new ExcelsiorCLI();
		application.run();
	}

	public ExcelsiorCLI() {
		this.menu = new Menu(System.in, System.out);
		
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

			if (choice.equals("1")) {
				showAllVenues();
				System.out.println();
			} else if (choice.equalsIgnoreCase("Q")) {
				System.exit(0);
			}
		}
	}

	private void showAllVenues() {
		List<Venue> venues = venueDao.getAllVenues();
		listVenues(venues);
	}

	private void listVenues(List<Venue> venues) {
		System.out.println();
		for (Venue venue : venues) {
			System.out.println(venue.getVenueId() + ") " + venue.getVenueName());
		}
	}
}
