package status;
import java.io.Serializable;
import java.util.ArrayList;

import basic.RoomCount;
import exceptions.RoomNotFoundException;

public class SynchStatus implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private UserStatus userStatus;
	private ArrayList<RoomCount> roomCountList;
	
	public SynchStatus(UserStatus userStatus,ArrayList<RoomCount> roomList)
	{
		this.userStatus = userStatus;
		this.roomCountList = roomList;
	}
	
	public void setUserStatus(UserStatus us) {
		userStatus = us;
	}
	
	public RoomCount findRoom(String idRoom) throws RoomNotFoundException {
		
		if(idRoom == null)
			throw new RoomNotFoundException();
		
		for(RoomCount rc : roomCountList)
			if(rc.getId().equals(idRoom))
				return rc;
		
		throw new RoomNotFoundException();
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public ArrayList<RoomCount> getRoomList() {
		return roomCountList;
	}

	public void update(SynchStatus updatedSynchStatus) {
		this.userStatus = updatedSynchStatus.userStatus;
		this.roomCountList = updatedSynchStatus.roomCountList;
		
	}


}
