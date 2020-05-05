package basic;

import java.io.Serializable;

public class Room implements Serializable {

	private static final long serialVersionUID = 1486633036256021265L;
	
	private String id;
    private String roomName;
    private int maxSize;
    private String roomPassword;	

    public void setId(String id) { this.id = id; }
	public String getId() { return id; }
	
	public void setRoomName(String roomName) { this.roomName = roomName; }
	public String getRoomName() { return roomName; }
	
	public void setMaxSize(int maxSize) { this.maxSize = maxSize; }
	public int getMaxSize() { return maxSize; }
	
	public void setRoomPassword(String roomPassword) { this.roomPassword = roomPassword; }
	public String getRoomPassword() { return roomPassword; }
	
	public boolean hasPassword() {
		return (roomPassword != null);
	}
	
	public boolean checkPassword(String input) {
		
		if(roomPassword == null)
			return true;
	
	    return (input != null && input.equals(roomPassword));

	}
 


}