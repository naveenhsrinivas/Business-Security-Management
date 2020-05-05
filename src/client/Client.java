package client;

import status.SynchStatus;
import threadClient.UserThread;

public class Client {
	
	public static void main(String[] args) {
		
		SynchStatus sstatus = new SynchStatus(null, null);
		UserThread ut = new UserThread(sstatus);
		ut.start();		
		
	}

}
