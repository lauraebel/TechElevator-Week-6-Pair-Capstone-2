package com.techelevator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.venue.Venue;

public class Menu {
	
	private PrintWriter out;
	private Scanner in;
	private List<Venue> venues;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}
	
	public String mainMenu() {
		boolean loop;
		String choice = null;
		
		System.out.println("What would you like to do?\n(1) List Venues\n(Q) Quit");
		while (choice == null) {
			loop = false;
			choice = in.nextLine();
		}
		return choice;
	}
}
	
	
	
//	public String getChoiceFromOptions(String[] options) {
//		String choice = null;
//		while(choice == null) {
//			displayMenuOptions(options);
//			choice = getChoiceFromUserInput(options);
//		}
//		return choice;
//	}
//	
//	private String getChoiceFromUserInput(String[] options) {
//		String choice = null;
//		String userInput = in.nextLine();
//		
//		try {
//			int selectedOption = Integer.valueOf(userInput);
//			if(selectedOption <= options.length) {
//				choice = options[selectedOption - 1];
//			}
//		} catch(NumberFormatException e) {
//		} if(choice == null) {
//			System.out.println(userInput + " is not a valid option. Please try again");
//		}
//		return choice;
//	}
//	
//	private void displayMenuOptions(String[] options) {
//		
//		System.out.println();
//		
//		for(int i = 0; i < options.length; i++) {
//			int number = i + 1;
//			System.out.println(number + ") " + options[i]);
//		}
	
	
	
	
	
	
	

	



