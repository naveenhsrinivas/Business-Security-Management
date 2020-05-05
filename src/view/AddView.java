package view;

import java.util.Scanner;

import basic.Room;
import basic.User;

public class AddView{
	
	private static Scanner scanner = new Scanner(System.in);
	
	private AddView() {}
	
	public static User newUser() {
		
		User newUser = new User();
		
		System.out.println("Add new user.");
		System.out.print("Please enter the user's first name: ");
	    newUser.setFirstName(scanner.next());
	    
	    System.out.print("Please enter the user's last name: ");
	    newUser.setLastName(scanner.next());
	    
		System.out.print("Please enter the user's e-mail: ");
		newUser.setEmailId(scanner.next());
		
		System.out.print("Please enter the user's designation: ");
		newUser.setDesignation(scanner.next());
		
		System.out.print("Please enter the user's department: ");
		newUser.setDepartment(scanner.next());
		
		System.out.print("Please enter the user's mobile number: ");
		newUser.setMobileNumber(scanner.next());
		
		System.out.print("Please enter the user's date of birth (YYYY-MM-DD): ");
		newUser.setDateOfBirth(scanner.next());
		
		System.out.print("Please enter the user's password: ");
		newUser.setPassword(scanner.next());
		
		System.out.print("Please enter the user's first pet's name: ");
		newUser.setSecurityQuestion1(scanner.next());
		
		System.out.print("Please enter the user's birth place: ");
		newUser.setSecurityQuestion2(scanner.next());
		
		return newUser;
		
	}
	
	public static Room newRoom() {
		
		Room newRoom = new Room();
		
		System.out.println("Add new room.");
		System.out.print("Please enter the room's name: ");
		newRoom.setRoomName(scanner.next());
		
		System.out.print("Please enter the room's capacity: ");
		newRoom.setMaxSize(scanner.nextInt());
		
		System.out.println("Do you want to set a password on this room? ");
		System.out.println("1) Yes\n0) No");
		int choiche = BasicView.askForChoice();
		
		if(choiche == 1) {
			System.out.print("Insert room's password: ");
			newRoom.setRoomPassword(scanner.next());
		}
		
		return newRoom;	
		
		
	}
	

}
