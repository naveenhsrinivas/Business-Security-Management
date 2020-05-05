package message;

import java.io.Serializable;

public class ClientMessage implements Serializable{
	
	private static final long serialVersionUID = -6060174920570864264L;
	
	public static final short LOGIN = 1;
	public static final short ENTER = 2;
	public static final short EXIT = 3;
	public static final short RECOVERY = 4;
	public static final short LOGOUT = 0;
	
	private String email; //login, recovery
	private String password; //login, enter, recovery
	private String idUser; //enter, exit, logout
	private String idRoom; //enter
	private String petName, birthPlace; //recovery
	
	private short type;
	
	private ClientMessage(short type, String email, String password, String idUser, String idRoom, String petName, String birthPlace) {
		
		this.type = type;
		this.email = email;
		this.password = password;
		this.idUser = idUser;
		this.idRoom = idRoom;
		this.petName = petName;
		this.birthPlace = birthPlace;
		
	}
	
	public static ClientMessage createLoginMessage(String email, String password) {
		return new ClientMessage(LOGIN, email, password, null, null, null, null);
	}
	
	public static ClientMessage createEnterMessage(String idUser, String idRoom, String password) {
		return new ClientMessage(ENTER, null, password, idUser, idRoom, null, null);
	}
	
	public static ClientMessage createExitMessage(String idUser) {
		return new ClientMessage(EXIT, null, null, idUser, null, null, null);
	}
	
	public static ClientMessage createLogoutMessage(String idUser) {
		return new ClientMessage(LOGOUT, null, null, idUser, null, null, null);
	}
	
	public static ClientMessage createRecoveryMessage(String email, String petName, String birthPlace, String newPassword) {
		return new ClientMessage(RECOVERY, email, newPassword, null, null, petName, birthPlace);
	}
	
	public short getType() {
		return type;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getIdUser() {
		return idUser;
	}

	public String getIdRoom() {
		return idRoom;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public String getPetName() {
		return petName;
	}	

}
