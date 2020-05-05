package log;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Log {
	
	public static final String DEFAULT = "-";
	private static final String ACCEPTED = "ACCEPTED";
	private static final String REJECTED = "REJECTED";
	
	private String filename = "log.txt";
	private FileWriter fw;
	private static Log instance = null;
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH_mm_ss");
	
	
	private Log() {
		try {
			 FileReader fileReader = new FileReader(filename);
			
			try {
				fw = new FileWriter(filename, true);
			} catch (IOException e) {
				System.out.println("Can't open log file");
			}
			
		} catch (FileNotFoundException e) {
			//if the file is not found, I have to create one and making the instestation
			
			try {
				fw = new FileWriter(filename);
				//intestation
				fw.write("timestamp\twho\taction\tparam\toutcome\tdescription\n");
				fw.flush();
			} catch (IOException e1) {
				System.out.println("Can't open log file");
				return;
			}
			
		}
	}
	
	public static Log getInstance() {
		if(instance == null)
			instance = new Log();
		return instance;
	}
	
	private String now() {
		
		return sdf.format(new Timestamp(System.currentTimeMillis()));
		
	}
	
	private String acceptedOrRejected(boolean outcome) {
		
		return ((outcome)?ACCEPTED:REJECTED);
				
	}
	
	private void writeLine(String email, String action, String param, boolean outcome, String description) {
		
		try {
			
			fw.write(now() + "\t" + email + "\t" + action + "\t" + param + "\t" + acceptedOrRejected(outcome) + "\t" + description + "\n");
			fw.flush();
			
		} catch (IOException e) {
			System.out.println("Log error");
		}
		
	}
	
	public void writeLogin(String email, boolean outcome, String description) {
		
		writeLine(email, "login", DEFAULT, outcome, description);
		
	}
	
	public void writeRecovery(String email, boolean outcome, String description) {
		
		writeLine(email, "recovery", DEFAULT, outcome, description);
		
	}
	
	public void writeEnter(String email, boolean outcome, String parameter, String description) {
		
		writeLine(email, "enter", parameter, outcome, description);
	}
	
	public void writeExit(String email, boolean outcome, String parameter, String description) {
		
		writeLine(email, "exit", parameter, outcome, description);
	}
	
	public void writeLogout(String email, boolean outcome, String description) {
		
		writeLine(email, "logout", DEFAULT, outcome, description);
		
	}
	
	

}
