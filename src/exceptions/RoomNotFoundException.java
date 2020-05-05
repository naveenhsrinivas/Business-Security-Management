package exceptions;

public class RoomNotFoundException extends Exception {
	
	private static final long serialVersionUID = -204742220798767381L;

	public RoomNotFoundException() {
		super("Room not found.");
	}

}
