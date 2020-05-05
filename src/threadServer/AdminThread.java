package threadServer;

import java.io.IOException;
import java.net.ServerSocket;

import server.Admin;
import status.Status;
import view.MenuView;

public class AdminThread extends Thread {
	
	private Admin admin;
	private ServerSocket server, synchServer;
	
	
	public AdminThread(Status status, ServerSocket server, ServerSocket synchServer) {
		admin = new Admin(status);
		this.server = server;
		this.synchServer = synchServer;
	}
	
	public void run() {
		
		int choice, internalChoice;
		
		
		do {
			
			choice = MenuView.mainMenu();					
			
			switch(choice) {
				
				case 1:
					admin.serverStatus();
					break;
				case 2:
					internalChoice = MenuView.addMenu();
					admin.add(internalChoice);
					break;
				case 3:
					internalChoice = MenuView.removeMenu();
					admin.remove(internalChoice);
					break;
				case 4:
					internalChoice = MenuView.showMenu();
					admin.show(internalChoice);
					break;
				case 5:
					admin.manageRoom();
					break;
			}
			
		} while(choice != 0);
		
		//stopping the other threads
		try {
			server.close();
			synchServer.close();
		} catch (IOException e) {
			
		}
		
		
		System.out.println("Goodbye.");
	}

}
