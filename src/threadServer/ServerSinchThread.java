package threadServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import server.Server;
import status.Status;

public class ServerSinchThread extends Thread{
	
	private ServerSocket serverSocket;
	private Status status;
	
	public ServerSinchThread(Status status) {
		try {
			serverSocket = new ServerSocket(Server.SYNCH_PORT);
		} catch (IOException e) {
			System.out.println("Error sinch thread socket.");
		}
		this.status = status;
	}
	
	public ServerSocket getSocket() {
		return serverSocket;
	}
	
	public void run() {
		
		while(true) {
			
			try {
				Socket socket = serverSocket.accept();
				ClientSinchHandler csh = new ClientSinchHandler(socket, status);
				csh.start();
			} catch (SocketException e) {
				return;
			} catch (IOException e) {
				System.out.println("Problem with accept of ServerSinchThread");
				System.exit(0);
			}
	
		}
	}
	
	
	

}
