package view;

public class MenuView {
	
	public static int mainMenu() {
		
		System.out.println("Select the operation... ");
		System.out.println("1) See Status of the building");
		System.out.println("2) Add new User/Room/Permission");
		System.out.println("3) Remove User/Room/Permission");
		System.out.println("4) Show User/Room/Permissions");
		System.out.println("5) Add/Update/Remove Room's password");
		BasicView.backOrQuit("Quit");
		return BasicView.askForChoice();
		
	}
	
	public static int addMenu() {
		
		System.out.println("Select what entity do you want to add...");
		System.out.println("1) User");
		System.out.println("2) Room");
		System.out.println("3) Permission");
		BasicView.backOrQuit("Back");		
		return  BasicView.askForChoice();
		
	}
	
	public static int removeMenu() {
		
		System.out.println("Select what entity do you want to remove...");
		System.out.println("1) User");
		System.out.println("2) Room");
		System.out.println("3) Permission");
		BasicView.backOrQuit("Back");
		return BasicView.askForChoice();
		
	}
	
	public static int showMenu() {
		
		System.out.println("Select what entity do you want to see the details...");
		System.out.println("1) User");
		System.out.println("2) Room");
		System.out.println("3) All Permissions");
		BasicView.backOrQuit("Back");
		return BasicView.askForChoice();
		
	}
	
	public static int hasRoomPassword() {
		
		System.out.println("This room has a password.");
		System.out.println("Do you want to remove or update the password? ");
		System.out.println("1) Remove");
		System.out.println("2) Update");
		BasicView.backOrQuit("Cancel");
		return BasicView.askForChoice();
	}
	
	public static int hasNoRoomPassword() {
		
		System.out.println("This room has no password.");
		System.out.println("Do you want to add the password? ");
		System.out.println("1) Add new Password");
		BasicView.backOrQuit("Cancel");
		return BasicView.askForChoice();
	}

}
