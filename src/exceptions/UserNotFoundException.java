package exceptions;

public class UserNotFoundException extends Exception {
	
	private static final long serialVersionUID = 5393804608081867957L;

	public UserNotFoundException() {
		super("User not found.");
	}

}
