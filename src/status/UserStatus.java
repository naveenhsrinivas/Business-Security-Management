package status;

import java.io.Serializable;

import basic.Room;
import basic.User;

public class UserStatus implements Serializable{

	private static final long serialVersionUID = -8682160853535360304L;
	
	private User user;
	private Room r;
	private boolean logged;
	
	public UserStatus(User u){
		this.user = u;
		r = null;
		logged = false;
	}
	
	public User getUser() {
		return user;
	}
	
	public Room getCurrentRoom() {
		return r;
	}
	
	public boolean isLogged() {
		return logged;
	}
	
	public boolean isInside() {
		return (r!=null);
	}
	
	public void login() {
		logged = true;
	}
	
	public void logout() {
		exitRoom();
		logged = false;
	}
	
	public void enterRoom(Room r) {
		this.r = r;
	}
	
	public void exitRoom() {
		r = null;
	}
	
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		
		str.append(user.getName() + " is ");
		str.append( (logged)? "" : "not " );
		str.append("logged in");
		if(r!=null)
			str.append(" and he is currently in room " + r.getRoomName());
		str.append(".");
		
		return str.toString();
		
	}

}
