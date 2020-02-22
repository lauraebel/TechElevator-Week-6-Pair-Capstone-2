package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import com.techelevator.venue.Venue;

public class Menu {
	
	private Scanner in = new Scanner(System.in);
	private List<Venue> venues;

	
	
//	public Menu(InputStream input, OutputStream output) {
//		this.out = new PrintWriter(output);
//		this.in = new Scanner(input);
//	}
	
	public String mainMenu() {
		boolean loop;
		String choice = null;
		
		System.out.println("What would you like to do?\n(1) List Venues\n(Q) Quit");
//		while (choice == null) {
//			loop = false;
			choice = in.nextLine();
//		}
		return choice;
	}
	
	public String venueMenu() {
		boolean loop;
		String choiceVenue = null;
		
		System.out.println("Which venue would you like to view?");
		while (choiceVenue == null) {
			loop = false;
			choiceVenue = in.nextLine();
		}
		return choiceVenue;
		
	}
}
	
	
	


	
	
	
	
	
	

	



