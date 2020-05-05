package basic;

public class Permission{
	
	private String idUser;
	private String idRoom;
	
	public Permission(String idUser, String idRoom) 
	{
		this.idUser = idUser;
		this.idRoom = idRoom;
	}
	

	public String getIdUser() { return idUser; }
    
    public String getIdRoom() { return idRoom; }
	
    
}
