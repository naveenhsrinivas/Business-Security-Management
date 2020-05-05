package threadServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import server.Server;
import status.Status;

public class ServerThread extends Thread {
	
	private ServerSocket serverSocket;
	private Status status;
	
	public ServerThread(Status status) {
		try {
			serverSocket = new ServerSocket(Server.PORT);
		} catch (IOException e) {
			System.out.println("Error server thread socket.");
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
				ClientHandler ch = new ClientHandler(socket, status);
				ch.start();
			} catch (SocketException e) {
				return;
			} catch (IOException e) {
				System.out.println("Problem with accept of ServerThread");
				System.exit(0);
			}
		}
	}

}
