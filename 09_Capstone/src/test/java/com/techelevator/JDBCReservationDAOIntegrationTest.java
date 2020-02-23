package com.techelevator;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.reservation.JDBCReservationDAO;
import com.techelevator.reservation.Reservation;
import com.techelevator.reservation.ReservationDAO;
import com.techelevator.state.State;

public class JDBCReservationDAOIntegrationTest {
	
	private ReservationDAO dao;
	private JdbcTemplate jdbcTemplate;
	private int reservationId;
	private static final String TEST_RESERVATION = "Fake Reservation";
	
	private static SingleConnectionDataSource dataSource;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior-venues");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "INSERT INTO reservation(reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (DEFAULT, 50, 100, '2020-06-19', '2020-07-05', 'Tim Smith') RETURNING reservation_id";
		
		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
		rows.next();
		reservationId = rows.getInt("reservation_id");
		
		dao = new JDBCReservationDAO(dataSource);
	}
	
	@Test
	public void select_reservation_by_id() {
		Reservation reservation = getReservation(33, 100, LocalDate.of(2020, 10, 29), LocalDate.of(2020, 12, 31), "stuff");
		dao.save(reservation);
		
		Reservation selectedReservation = dao.findByReservationId(reservation.getReservationId());
		Assert.assertNotNull("returned null", selectedReservation);
		
		Assert.assertEquals("space if not equal", reservation.getSpaceId(), selectedReservation.getSpaceId());
		Assert.assertEquals("attendees not equal", reservation.getNumberOfAttendees(), selectedReservation.getNumberOfAttendees());
		Assert.assertEquals("start date not equal", reservation.getStartDate(), selectedReservation.getStartDate());
		Assert.assertEquals("end dates not equal", reservation.getEndDate(), selectedReservation.getEndDate());
		Assert.assertEquals("reserved for not equal", reservation.getReservedFor(), selectedReservation.getReservedFor());
	}
	
	@Test
	public void insert_reservation() {
		Reservation reservation = getReservation(33, 100, LocalDate.of(2020, 10, 29), LocalDate.of(2020, 12, 31), "stuff");
		dao.save(reservation);
		
		Assert.assertNotEquals("Id not created", 0, reservation.getReservationId());
		
		Reservation selectedReservation = dao.findByReservationId(reservation.getReservationId());
		
		Assert.assertNotNull("inserted res is null", selectedReservation);
		Assert.assertEquals("space id is not equal", reservation.getSpaceId(), selectedReservation.getSpaceId());
		Assert.assertEquals("attendees not equal", reservation.getNumberOfAttendees(), selectedReservation.getNumberOfAttendees());
		Assert.assertEquals("start date not equal", reservation.getStartDate(), selectedReservation.getStartDate());
		Assert.assertEquals("end dates not equal", reservation.getEndDate(), selectedReservation.getEndDate());
		Assert.assertEquals("reserved for not equal", reservation.getReservedFor(), selectedReservation.getReservedFor());
	}
	
	@Test 
	public void updating_number_of_attendees() {
		Reservation reservation = getReservation(33, 100, LocalDate.of(2020, 10, 29), LocalDate.of(2020, 12, 31), "stuff");
		dao.save(reservation);
		
		reservation.setNumberOfAttendees(400);
		dao.update(reservation);
		
		Reservation updatedReservation = dao.findByReservationId(reservation.getReservationId());
		
		Assert.assertNotNull("updatedReservation is null", updatedReservation);
		Assert.assertEquals("updated attendance is not equal", reservation.getNumberOfAttendees(), updatedReservation.getNumberOfAttendees());	
	}
	
	@Test
	public void updating_end_date() {
		Reservation reservation = getReservation(33, 100, LocalDate.of(2020, 10, 29), LocalDate.of(2020, 12, 31), "stuff");
		dao.save(reservation);

		reservation.setEndDate(LocalDate.of(2030, 11, 19));
		dao.update(reservation);
		
		Reservation updatedReservation = dao.findByReservationId(reservation.getReservationId());
		
		Assert.assertNotNull("updated reservation is null", updatedReservation);
		Assert.assertEquals("updated end dates not equal", reservation.getEndDate(), updatedReservation.getEndDate());
	}
	
	@Test
	public void deleting_a_reservation() {
		Reservation reservation = getReservation(33, 100, LocalDate.of(2020, 10, 29), LocalDate.of(2020, 12, 31), "stuff");
		dao.save(reservation);
		
		dao.delete(reservation.getReservationId());
		
		Reservation deletedReservation = dao.findByReservationId(reservation.getReservationId());
		
		Assert.assertNull("reservation not deleted", deletedReservation);
	}
	
	@Test
	public void selecting_reservation_by_reserved_for() {
		Reservation reservation = getReservation(33, 100, LocalDate.of(2020, 10, 29), LocalDate.of(2020, 12, 31), "stuff");
		dao.save(reservation);
		
		Reservation selectedReservation = dao.findByReservationId(reservation.getReservationId());
		
		Assert.assertNotNull("state is null", selectedReservation);
		Assert.assertEquals("abbreviations not equal", reservation.getReservedFor(), selectedReservation.getReservedFor());
	}
	
	
	@Test
	public void shows_all_reservations() {
		String sqlShowAllReservation = "INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) "
				+ "VALUES (78, 6, 100, '2020-03-15', '2020-06-09', ?) ";
		jdbcTemplate.update(sqlShowAllReservation, TEST_RESERVATION);
		
		boolean showsRes = false;
		
		for(Reservation reservations : dao.getAllReservations()) {
			String example = reservations.getReservedFor();
			if(example.equals("Fake Reservation")) {
				showsRes = true;
			}
		}
		Assert.assertTrue(showsRes);
	}
	
	private Reservation getReservation(long spaceId, int numberOfAttendees, LocalDate startDate, LocalDate endDate, String reservedFor) {
		Reservation selectedReservation = new Reservation();
		selectedReservation.setSpaceId(33);
		selectedReservation.setNumberOfAttendees(800);
		selectedReservation.setStartDate(startDate);
		selectedReservation.setEndDate(endDate);
		selectedReservation.setReservedFor("people");
		return selectedReservation;
	}
}
