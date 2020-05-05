package view;

import java.util.ArrayList;
import java.util.Scanner;

import basic.RoomCount;
import basic.User;
import status.SynchStatus;
import status.UserStatus;

public class ClientView {
	
	private static Scanner scanner = new Scanner(System.in);
	
	private ClientView() {}
	
	public static int notLoggedMenu() {
		
		System.out.println("Welcome to the BSM system.");
		System.out.println("Choose your operation.");
		System.out.println("1) Login");
		System.out.println("2) Recovery Password");
		BasicView.backOrQuit("Quit");
		
		return BasicView.askForChoice();
		
	}
	
	public static int loggedMenu(UserStatus us) {
		
		System.out.println("Hello " + us.getUser().getName() +"!");
		System.out.println(((!us.isInside())? "You are inside the building." : "You are in room \"" + us.getCurrentRoom().getRoomName() + "\"."));
		System.out.println("Choose your operation.");
		System.out.println("1) Enter a room");
		System.out.println("2) Exit room");
		System.out.println("3) Show my information");
		BasicView.backOrQuit("Logout");
		
		return BasicView.askForChoice();
		
	}
	
	public static void showInformation(SynchStatus ss) {
		
		User user = ss.getUserStatus().getUser();
		ArrayList<RoomCount> roomCountList = ss.getRoomList();
		
		System.out.println("Showing your information...");
		System.out.println("Name: " + user.getName());
		System.out.println("DoB: "+ user.getDateOfBirth());
		System.out.println("E-mail:  " + user.getEmailId());
		System.out.println("Designation: " + user.getDesignation());
		System.out.println("Department: " + user.getDepartment());
	
		System.out.println("You can enter the following rooms: ");
		for(RoomCount rc :  roomCountList)
			System.out.println(rc.getRoomName());
		System.out.println("END.");
		
	}

	public static String getString(String string) {
		
		System.out.print(string);
		return scanner.next();
	}

}
