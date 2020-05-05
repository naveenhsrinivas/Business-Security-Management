package view;

import java.util.Scanner;

import message.ServerMessage;

public class BasicView {
	
	protected static Scanner scanner = new Scanner(System.in);

	public static int askForChoice() {
		
		System.out.print("Choice: ");		
		return scanner.nextInt();
	}
	
	public static String askForId() {
		
		System.out.print("Id: ");
		return scanner.next();
	}
	
	public static void backOrQuit(String str) {
		
		System.out.println();
		System.out.println("0) " + str);
		System.out.println();
	}

	
	public static void confirmOrAbort(boolean outcome) {
		if(outcome)
			System.out.println("Operation completed successfully.");
		else
			System.out.println("Operation NOT completed.");
	}
	
	public static void serverAnswer(ServerMessage sermsg) {
		if(sermsg.getType() == ServerMessage.ACCEPT)
			System.out.println("Operation accepted and completed.");
		else
			System.out.println("Operation rejected because: " + sermsg.getMessage());
	}
	
	public static void cantCompleteOperation(String why) {
		System.out.println("Sorry! I can't complete this operation.");
		System.out.println("Reason: " + why + ".");
	}
	
	public static String getString(String message) {
		System.out.print(message);
		return scanner.next();
	}

}
