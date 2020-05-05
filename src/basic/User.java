package basic;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 538026338908478551L;
	
	private String firstName;
    private String lastName;
    private String id;
    private String logIn;
    private String emailId;
    private String password;
    private String designation;
    private String department;
    private String mobileNumber;
    private String dateOfBirth;
    private String securityQuestion1;
    private String securityQuestion2;
	
	public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getName() { return firstName + " " +  lastName; }
    
    public void setId(String id) { this.id = id; }
    public String getId() { return id; }
    
    public void setLogIn(String logIn) { this.logIn = logIn; }
    public String getLogIn() { return logIn; }
    
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public String getEmailId() { return emailId; }
    
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }
    
    public void setDepartment(String department) { this.department = department; }
    public String getDepartment() { return department; }
    
    public void setDesignation(String designation) { this.designation = designation; }
    public String getDesignation() { return designation; }
    
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getMobileNumber() { return mobileNumber; }
    
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getDateOfBirth() { return dateOfBirth; }
    
    public void setSecurityQuestion1(String securityQuestion1) { this.securityQuestion1 = securityQuestion1; }
    public String getSecurityQuestion1() { return securityQuestion1; }
    
    public void setSecurityQuestion2(String securityQuestion2) { this.securityQuestion2 = securityQuestion2; }
    public String getSecurityQuestion2() { return securityQuestion2; }
    
    public boolean checkPassword(String input) {
    	return (input != null && password.equals(input));
    }

}

