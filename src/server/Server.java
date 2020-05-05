package server;

import status.Status;
import threadServer.AdminThread;
import threadServer.ServerSinchThread;
import threadServer.ServerThread;

public class Server {
	
	public final static String ADDRESS = "localhost";
	public final static int PORT = 5000;
	public final static int SYNCH_PORT = 4000;

	public static void main(String[] args){
		
		Status status = new Status();
		
		ServerThread st = new ServerThread(status);
		ServerSinchThread sst = new ServerSinchThread(status);
		
		AdminThread admthr = new AdminThread(status, st.getSocket(), sst.getSocket());
		
		admthr.start();
		st.start();
		sst.start();		

	}

}
