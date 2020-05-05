package threadClient;

import client.UserController;
import status.SynchStatus;
import view.ClientView;

public class UserThread extends Thread {
	
	private SynchStatus synchStatus;
	private UserController userController;
	private boolean logged,insideARoom;
	
	public UserThread(SynchStatus synchStatus) {
		this.synchStatus = synchStatus;
		userController = new UserController(synchStatus);
		logged = false;
		insideARoom = false;
	}
	
	@Override
	public void run() {
		
		int choice;
		boolean out = false;
		
		do {
			
			if(!logged) {
				
				insideARoom = false;
				choice = ClientView.notLoggedMenu(); 
				
				switch (choice) {
					
					case 1: //login
						logged = userController.login();						
						break;
					
					case 2: //recovery
						userController.recoveryPassword();
						break;
					
					case 0: //close program
						out = true;
						break;
	
					default:
						break;
				}
				
				
			}
			else {
				
				choice = ClientView.loggedMenu(synchStatus.getUserStatus());
				
				switch (choice) {
					
					case 1: //enter
						
						if(!insideARoom)
							insideARoom = userController.enter();
						else
							System.out.println("You are already inside a room, you need to exit first.");						
						break;
						
					case 2: //exit
						if(insideARoom)
							insideARoom = ! userController.exit(); // because if it is correct, he's no longer inside
						else
							System.out.println("You aren't inside any room, you need to enter one first.");
						break;
						
					case 3:
						ClientView.showInformation(synchStatus);
						break;
					
					case 0:
						logged = ! userController.logout();
						break;
					
						

					default:
						break;
				}
			}
			
			
			
		} while(!out);
		
		System.out.println("Goodbye.");
		
	}

}
