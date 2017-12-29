package view.consoleUI;

import java.io.*;
import java.util.*;

import controller.Controller;
import view.UserInterface;

public class CUIManager implements UserInterface {
	
	public CUIManager() {
		
	}
	
	public UserInterface run() {
		
		while(!Controller.rosterIsLoaded()) {
			try {
				String loc = welcomeSequence();
				Controller.loadRoster(loc);
			} catch(IOException ioe) {
				Console.println("Invalid path or file name");
			}
		}
		
		mainSequence();
		
		return this;
		
	}
	
	private String welcomeSequence() {
		
		Console.printTitle("Welcome");
		Console.println("Load a roster or make a new one");
		Console.println("1. Load Roster");
		Console.println("2. New Roster");
		Console.println("3. Exit");
		
		int option = Console.promptOption(3);
		switch(option) {
		case 1:
			return loadRosterSequence();
		case 2:
			return newRosterSequence();
		case 3:
			System.exit(0);
		}
		
		return "";
		
	}

	private String loadRosterSequence() {
		
		Console.printTitle("Load Roster");
		Console.printLines(1);
		Console.println("Warning: Not using the full path may cause problems");
		Console.printLines(1);
		
		return Console.promptString("Roster Path >> ");
		
	}
	
	private String newRosterSequence() {
		
		Console.printTitle("New Roster");
		Console.printLines(1);
		Console.println("Warning: Inputting a relative path may cause problems");
		Console.printLines(1);
		
		String dir = Console.promptString("Roster Directory >> ");
		String name = Console.promptString("File Name >> ");
		
		if(!dir.endsWith(File.separator)) dir += File.separator;
		if(!name.endsWith(".xml")) name += ".xml";
		return dir + name;
		
	}
	
	private void mainSequence() {
		
		Console.printTitle("ON A ROLL");
		Console.println("What would you like to do?");
		Console.println("1. Manage Roster");
		Console.println("2. Mark Roster");
		Console.println("3. Print Roster Summary");
		Console.println("4. Save Roster");
		Console.println("5. Exit");
		
		int option = Console.promptOption(5);
		switch (option) {
		case 1:
			manageRosterSequence();
			break;
		case 2:
			markRosterSequence();
			break;
		case 3:
			printRosterSequence();
			break;
		case 4:
			saveRosterSequence();
			break;
		case 5:
			System.exit(0);
		}
	}
	
	private void manageRosterSequence() {
		
		Console.printTitle("Manage Roster");
		Console.println("What would you like to do?");
		Console.println("1. Add a member");
		Console.println("2. Remove a member");
//		Console.println("3. Add member info");
//		Console.println("4. Edit member info");
//		Console.println("5. Remove member info");
//		Console.println("6. Add task to member");
//		Other task related stuff
		Console.println("3. Return to Main Menu");
		Console.println("4. Exit");
		
		int option = Console.promptOption(4);
		
		switch(option) {
		case 1:
			addMemberSequence();
		case 2:
			removeMemberSequence();
		case 3:
			mainSequence();
		case 4:
			System.exit(0);
		}
		
	}

	private void addMemberSequence() {
		
		Console.printTitle("Add Member");
		Console.println("Input the name of the member\nthat you would like to add");
		
		String name = Console.promptString("Name >> ");
		
		Controller.addMember(name);
		
	}
	
	private void removeMemberSequence() {
		
		Console.printTitle("Remove Member");
		Console.println("Input the name of the member\nthat you would like to remove");
		
		String name = Console.promptString("Name >> ");
		
		Controller.removeMember(name);
		
	}

	private void markRosterSequence() {
		
		Console.printTitle("Mark Roster");
		Console.println("What would you like to do?");
		Console.println("1. Mark all members");
		Console.println("2. Mark one member");
		Console.println("3. Mark all members for a previous day");
		Console.println("4. Mark one member for a previous day");
		Console.println("5. Return to the Main Menu");
		Console.println("6. Exit");
		
		int option = Console.promptOption(6);
		
		switch(option) {
		case 1:
			markAllSequence();
			break;
		case 2:
			markOneSequence();
			break;
		case 3:
			markAllPreviousDaySequence();
			break;
		case 4:
			markOnePreviousDaySequence();
		case 5:
			mainSequence();
		case 6:
			System.exit(0);
		}
		
	}
	
	private void markAllSequence() {
		
		Console.printTitle("Mark All");
		Console.println("Input the marking for each member after their name");
		Console.println("Input 'p' to mark present and 'a' to mark absent");
		Console.printLines(1);
		
		LinkedList<String> names = Controller.getNames();
		HashMap<String, String> attendance = new HashMap<String, String>();
		
		if(names.size() == 0)
			Console.println("There are no members on the roster\n"
					+ "Choose 'Manage Roster' to add members");
		
		for(String name : names) {
			String marking = Console.promptMarking(name + " >> ", Controller.MARKS);
			attendance.put(name, marking);
		}
		
		Controller.markAttendance(attendance, new GregorianCalendar());
		
		mainSequence();
		
	}

	private void markOneSequence() {
		
		Console.printTitle("Mark One");
		Console.println("Input the name of the member\nthat you would like to mark");
		
		String name = Console.promptString("Name >> ");
		
		Console.println("Input the marking");
		Console.println("Input 'p' to mark present");
		
		String marking = Console.promptMarking("Marking >> ", Controller.MARKS);
		
		boolean success = Controller.markAttendance(name, marking, new GregorianCalendar());
		if(!success) Console.println("Member does not exist\n");
		
		mainSequence();
	}
	
	private void markAllPreviousDaySequence() {
		
		Console.printTitle("Mark All");
		Console.println("Input the date");
		
		int[] date = Console.promptDate();
		
		LinkedList<String> names = Controller.getNames();
		HashMap<String, String> attendance = new HashMap<String, String>();
		
		Console.printTitle("Mark All");
		Console.println("Input the marking for each member after their name");
		Console.println("Input 'p' to mark present");
		Console.printLines(1);
		
		if(names.size() == 0)
			Console.println("There are no members on the roster\n"
					+ "Choose 'Manage Roster' to add members");
		
		for(String name : names) {
			String marking = Console.promptMarking(name + " >> ", Controller.MARKS);
			attendance.put(name, marking);
		}
		
		Controller.markAttendance(attendance, new GregorianCalendar(date[Console.YEAR], --date[Console.MONTH], date[Console.DAY]));
		
		mainSequence();
		
	}
	
	private void markOnePreviousDaySequence() {
		
		Console.printTitle("Mark All");
		Console.println("Input the date");
		
		int[] date = Console.promptDate();
		
		Console.println("Input the name of the member\nthat you would like to mark");
		String name = Console.promptString("Name >> ");
		
		Console.println("Input the marking");
		Console.println("Input 'p' to mark present");
		String marking = Console.promptMarking("Marking >> ", Controller.MARKS);
		
		boolean success = Controller.markAttendance(name, marking, new GregorianCalendar(date[Console.YEAR], --date[Console.MONTH], date[Console.DAY]));
		if(!success) Console.println("Member does not exist\n");
		
		mainSequence();
		
	}

	private void printRosterSequence() {
		Console.printTitle("Roster");
		Console.println(Controller.rosterToString());
		
		mainSequence();
	}
	
	private void saveRosterSequence() {
		
		// Alert where the roster is being saved to
		Console.printTitle("Save Roster");
		Console.print("Saving roster to: ");
		Console.println(Controller.rosterLocation());
		
		// Try to save the roster
		try {
			Controller.saveRoster();
			Console.println("Saved!");
		} catch(IOException ioe) {
			Console.print("Error saving roster: ");
			Console.println(ioe.getMessage());
		}
		
		// Return to the main sequence
		mainSequence();
		
	}

}
