package com.techelevator;

import java.util.List;

import javax.sql.DataSource;

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
	List<Venue> venues;
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/Excelsior-Venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		ExcelsiorCLI application = new ExcelsiorCLI(dataSource);
		application.run();
	}

	public ExcelsiorCLI(DataSource datasource) {
		categoryDao = new JDBCCategoryDAO(datasource);
		cityDao = new JDBCCityDAO(datasource);
		reservationDao = new JDBCReservationDAO(datasource);
		spaceDao = new JDBCSpaceDAO(datasource);
		stateDao = new JDBCStateDAO(datasource);
		venueDao = new JDBCVenueDAO(datasource);
	}

	public void run() {
		int choice = menu.mainMenu(venues);
		while(true) {
			if (choice == 1) {
				venueDao.getAllVenues();
			} else {
				System.exit(0);
			}
			
		}

	}
	

}
