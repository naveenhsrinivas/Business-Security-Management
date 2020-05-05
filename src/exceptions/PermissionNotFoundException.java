package exceptions;

public class PermissionNotFoundException extends Exception {
	
	private static final long serialVersionUID = 8334046157758465235L;

	public PermissionNotFoundException() {
		super("Permission not found.");
	}

}
