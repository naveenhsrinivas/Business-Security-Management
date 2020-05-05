package threadServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import status.Status;
import status.SynchStatus;

public class ClientSinchHandler extends Thread{
	
	private Socket socket;
	private Status status;
	
	public ClientSinchHandler(Socket socket, Status status) {
		this.socket = socket;
		this.status = status;
	}
	
	@Override
	public void run() {
		
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		String idUser = null;
		SynchStatus ss = null;
		
		try {
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			System.out.println("Problem creating OOS or OIS from Server Sinch Handler.");
			return;
		}
		
		try {
			idUser = (String) ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Error reading idUser in Server Sinch Handler");
			return;
		}
		
		ss = status.getSynchStaus(idUser);
		
		try {
			oos.writeObject(ss);
		} catch (IOException e) {
			System.out.println("Error sending synch Status.");
			return;
		}			
		
		
	}

}
