package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import basic.RoomCount;
import exceptions.RoomNotFoundException;
import message.ClientMessage;
import message.ServerMessage;
import server.Server;
import status.SynchStatus;
import threadClient.SynchThread;
import view.BasicView;
import view.ClientView;
import view.SelectView;

public class UserController {
	
	private SynchStatus synchStatus;
	private SynchThread synchThread;
	

	public UserController(SynchStatus synchStatus) {
		this.synchStatus = synchStatus;
	}
	
	private ServerMessage send(ClientMessage clientMessage) {
		
		Socket socket = null;
		ServerMessage serverMessage = null;
		
		try {
			socket = new Socket(Server.ADDRESS, Server.PORT);
		} catch (IOException e) {
			System.out.println("Error creating socket message");
			System.exit(0);
		}
		
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		try {
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			System.out.println("Problem creating OOS or OIS from Client Server.");
		}
		
		try {
			oos.writeObject(clientMessage);
		} catch (IOException e) {
			System.out.println("Error send message client");
		}
		
		try {
			serverMessage = (ServerMessage) ois.readObject();
		} catch (Exception e) {
			System.out.println("Error receiving server message");
		}
		
		
		try {
			socket.close();
		} catch (IOException e1) {
			System.out.println("Error closing synchthread socket");
		}
		
		return serverMessage;
		
	}
	
	public boolean login() {
		
		String email = ClientView.getString("Insert your email: ");
		String password = ClientView.getString("Insert your password: ");
		
		ClientMessage clientMessage = ClientMessage.createLoginMessage(email, password);
		
		ServerMessage serverMessage = send(clientMessage);
		boolean outcome = serverMessage.getType() == ServerMessage.ACCEPT;
		
		BasicView.serverAnswer(serverMessage);
		
		if(outcome) {
			synchStatus.setUserStatus(serverMessage.getUserStatus());
			synchThread = new SynchThread(synchStatus);
			synchThread.setUserId(serverMessage.getUserStatus().getUser().getId());
			synchThread.start();
		}
		
		return outcome;
	}

	public void recoveryPassword() {
		
		String email = ClientView.getString("Insert your email: ");
		String petName = ClientView.getString("Insert your first pet's name: ");
		String birthPlace = ClientView.getString("Insert your birth place: ");
		String newPassword = ClientView.getString("Insert your new password: ");
		
		ClientMessage clientMessage = ClientMessage.createRecoveryMessage(email, petName, birthPlace, newPassword);
		
		ServerMessage serverMessage = send(clientMessage);

		BasicView.serverAnswer(serverMessage);
		
	}
	
	public boolean enter() {
		
		if(synchStatus.getRoomList().isEmpty()) {
			System.out.println("You can't enter any room.");
			return false;
		}
		
		String idRoom = SelectView.selectRoomClient(synchStatus.getRoomList());
		String roomPassword = null;
		
		try {
			
			RoomCount rc = synchStatus.findRoom(idRoom);
			if(rc.hasPassword())
				roomPassword = BasicView.getString("Insert room's password: ");
			
			ClientMessage clientMessage = ClientMessage.createEnterMessage(synchStatus.getUserStatus().getUser().getId(), idRoom, roomPassword);
			
			ServerMessage serverMessage = send(clientMessage);
			boolean outcome = serverMessage.getType() == ServerMessage.ACCEPT;
			
			BasicView.serverAnswer(serverMessage);
			
			if(outcome) {
				synchStatus.setUserStatus(serverMessage.getUserStatus());			
			}
			
			return outcome;
			
			
		} catch (RoomNotFoundException e) {
			System.out.println("No Room found with that ID");
		}
		
		return false;
		
		
	}

	public boolean exit() {
		
		ClientMessage clientMessage = ClientMessage.createExitMessage(synchStatus.getUserStatus().getUser().getId());
		ServerMessage serverMessage = send(clientMessage);
		boolean outcome = serverMessage.getType() == ServerMessage.ACCEPT;
		
		BasicView.serverAnswer(serverMessage);
		
		if(outcome) {
			synchStatus.setUserStatus(serverMessage.getUserStatus());			
		}
		
		return outcome; 
	}

	public boolean logout() {

		ClientMessage clientMessage = ClientMessage.createLogoutMessage(synchStatus.getUserStatus().getUser().getId());
		ServerMessage serverMessage = send(clientMessage);
		boolean outcome = serverMessage.getType() == ServerMessage.ACCEPT;
		
		BasicView.serverAnswer(serverMessage);
		
		if(outcome) {
			synchThread.stopSynching();
			synchStatus.setUserStatus(serverMessage.getUserStatus());
		}
		
		return outcome;
	}
	
	
	

}
