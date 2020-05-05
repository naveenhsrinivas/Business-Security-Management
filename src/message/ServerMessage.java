package message;

import java.io.Serializable;

import status.UserStatus;

public class ServerMessage implements Serializable{
	
	private static final long serialVersionUID = 4496942655607309760L;
	
	public static final short ACCEPT = 1;
	public static final short REJECT = 0;
	
	private short type;
	private String message;
	private UserStatus userStatus;
	
	private ServerMessage(short type, String message, UserStatus userStatus) {
		this.type = type;
		this.message = message;
		this.userStatus = userStatus;
	}
	
	public static ServerMessage createAcceptMessage(UserStatus userStatus) {
		return new ServerMessage(ACCEPT, null, userStatus);
	}
	
	public static ServerMessage createRejectMessage(String message) {
		return new ServerMessage(REJECT, message, null);
	}

	public short getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

}
