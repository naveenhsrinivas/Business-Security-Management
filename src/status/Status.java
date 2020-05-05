package status;

import java.util.ArrayList;

import basic.Permission;
import basic.Room;
import basic.RoomCount;
import basic.User;
import exceptions.PermissionNotFoundException;
import exceptions.RoomNotFoundException;
import exceptions.UserNotFoundException;
import model.Model;

public class Status {
	
	private ArrayList<UserStatus> userStatusList;
	private ArrayList<Room> roomList;
	private Permissions permissionList;

	public Status(){ 
		userStatusList = new ArrayList<UserStatus>();
		fillUserStatusList();
		roomList = Model.loadRooms();
		permissionList = new Permissions();
	}
	
	private void fillUserStatusList() {
		
		ArrayList<User> userList = Model.loadUsers();
		
		for(int i = 0; i < userList.size(); i++)
			userStatusList.add(new UserStatus(userList.get(i)));			

	}
	
	/*** 				USER 				***/
	
	private int findUser(String userId) throws UserNotFoundException {
		
		if(userId == null)
			throw new UserNotFoundException();
		
		for(int i = 0; i < userStatusList.size(); i++)
			if(userStatusList.get(i).getUser().getId().equals(userId)) 
				return i;
		
		//if I cannot find it
		throw new UserNotFoundException();
				
	}
	
	public UserStatus getUserStatus(String userId) throws UserNotFoundException {
		
		return userStatusList.get(findUser(userId));
	}
	
	public UserStatus getUserStatusByEmail(String email) throws UserNotFoundException {
		
		if(email == null)
			throw new UserNotFoundException();
		
		for(UserStatus us : userStatusList)
			if(us.getUser().getEmailId().equals(email))
				return us;
		
		//if I cannot find it
		throw new UserNotFoundException();
	}
	
	public boolean updateUserPassword(String email, String newPassword) throws UserNotFoundException {
		User user = getUserStatusByEmail(email).getUser();
		boolean outcome = Model.updateUserPassword(email, newPassword);
		if(outcome)
			user.setPassword(Model.getHashedPassword(newPassword));
		return outcome;
	}
	
	public boolean addNewUser(User newUser, String password) {
		
		try {
			
			findUser(newUser.getId());
			
		} catch (UserNotFoundException e) {
			//if there is no user with that ID I can add them
			if(Model.insertNewUser(newUser, password)) {
				userStatusList.add(new UserStatus(newUser));
				return true;
			}
		}
		
		return false;
	}
	
	public int userCount() {
		return userStatusList.size();
	}
	
	public void removeUser(String userId) throws UserNotFoundException {	

		userStatusList.remove(findUser(userId));
		permissionList.removeAllPermissionsOfUser(userId);
		Model.deleteUser(userId);
	}
	
	public ArrayList<Room> permitted(User u) {
		
		ArrayList<String> idrooms =  permissionList.getAccessableRooms(u);
		ArrayList<Room> allRooms = new ArrayList<Room>();
		
		for(int i = 0; i < roomList.size(); i++)
			for(int j = 0; j < idrooms.size(); j++)
				if(idrooms.get(j).equals(roomList.get(i).getId()))
					allRooms.add(roomList.get(i));
		return allRooms;
		
	}
	
	/*** 				ROOM				***/
	
	private int findRoom(String roomId) throws RoomNotFoundException {
		
		if(roomId == null)
			throw new RoomNotFoundException();
		
		for(int i = 0; i < roomList.size(); i++)
			if(roomList.get(i).getId().equals(roomId)) 
				return i;
		
		//if I cannot find it
		throw new RoomNotFoundException();
				
	}
	
	public Room getRoom(String roomId) throws RoomNotFoundException {
		
		return roomList.get(findRoom(roomId));
	}
	
	public boolean addNewRoom(Room newRoom, String password) {
		
		try {
			
			findRoom(newRoom.getId());
			
		} catch (RoomNotFoundException e) {
			//if there is no user with that ID I can add them
			if(Model.insertNewRoom(newRoom, password)) {
				roomList.add(newRoom);
				return true;
			}
		}
		
		return false;
	}
	
	public int roomCount() {
		return roomList.size();
	}
	
	public boolean updateRoomPassword(String idRoom, String newPassword) throws RoomNotFoundException {
		
		Room currentRoom = roomList.get(findRoom(idRoom));
		boolean outcome = Model.updateRoomPassword(idRoom, newPassword);
		
		if(outcome) {
			
			if(newPassword == null)
				currentRoom.setRoomPassword(null);
			else
				currentRoom.setRoomPassword(Model.getHashedPassword(newPassword));
		}
			
		return outcome;
		
	}
	
	public void removeRoom(String roomId) throws RoomNotFoundException {	
		
		roomList.remove(findRoom(roomId));
		permissionList.removeAllPermissionsOfRoom(roomId);
		Model.deleteRoom(roomId);
		
	}
	
	public ArrayList<User> permitted(Room r) {
		
		ArrayList<String> idUsers =  permissionList.getAllowedUsers(r);
		ArrayList<User> allUsers = new ArrayList<User>();
		
		for(int i = 0; i < userStatusList.size(); i++)
			for(int j = 0; j < idUsers.size(); j++)
				if(userStatusList.get(i).getUser().getId().equals(idUsers.get(j)))
					allUsers.add(userStatusList.get(i).getUser());
		return allUsers;
		
	}
	
	/***				PERMISSIONS 		***/
	
	public void showAllPermissions() {
		
		ArrayList<Permission> list = permissionList.getList();
		
		for(int i = 0; i < list.size(); i++) {
			try {
				System.out.println( userStatusList.get(findUser(list.get(i).getIdUser())).getUser().getName() +
						" - " + roomList.get(findRoom(list.get(i).getIdRoom())).getRoomName());
			} catch (Exception e) {
			}
		}
		
	}
	
	public boolean isUserAllowed(String idUser, String idRoom) {
		
		try {
			permissionList.getPermission(idUser, idRoom);
			return true;
		} catch (PermissionNotFoundException e) {
			return false;
		}
	}
	
	public boolean addNewPermission(String idUser, String idRoom) {
		return permissionList.addNewPermission(new Permission(idUser, idRoom));
	}
	
	public void removePermission(String idUser, String idRoom) throws PermissionNotFoundException {
		permissionList.removePermission(idUser, idRoom);
	}
	
	/***				MIXED				***/	
	
	public ArrayList<User> getAllUsers(){
		
		ArrayList<User> list = new ArrayList<User>();
		
		for(int i = 0; i < userStatusList.size(); i++)
			list.add(userStatusList.get(i).getUser());
		
		return list;
	}
	
	public ArrayList<Room> getAllRoom(){
		return roomList;
	}
	
	public ArrayList<User> getLoggedOrNotUsers(boolean logged){
		
		ArrayList<User> list = new ArrayList<User>();
		
		for(int i = 0; i < userStatusList.size(); i++)
			if(userStatusList.get(i).isLogged() == logged && logged == true && userStatusList.get(i).getCurrentRoom() == null)
				list.add(userStatusList.get(i).getUser());
			else if (userStatusList.get(i).isLogged() == logged && logged == false)
				list.add(userStatusList.get(i).getUser());
		
		return list;
		
	}
	
	public ArrayList<RoomStatus> getAllRoomsStatus(){
		
		ArrayList<RoomStatus> list = new ArrayList<RoomStatus>();
		
		for(int i = 0; i < roomList.size(); i++) {
			
			list.add(new RoomStatus(roomList.get(i)));
			
			for(int j = 0; j < userStatusList.size(); j++)				
				if(userStatusList.get(j).getCurrentRoom() == roomList.get(i))
					list.get(i).add(userStatusList.get(j).getUser());			
		}
		
		return list;
	}
	
	public RoomStatus getRoomStatus(String idRoom) throws RoomNotFoundException {
		
		RoomStatus rs = new RoomStatus(roomList.get(findRoom(idRoom)));
		
		for(int i = 0; i < userStatusList.size(); i++)
			if(userStatusList.get(i).getCurrentRoom() == rs.getRoom())
				rs.add(userStatusList.get(i).getUser());
		
		return rs;
	}
	
	
	public void print() {
		
		for(int i = 0; i < userCount(); i++)
			System.out.println(userStatusList.get(i));
		
	}
	
	public SynchStatus getSynchStaus(String idUser)	
	{
		ArrayList<Permission> list = permissionList.findUserPermission(idUser);
		
		ArrayList<RoomCount> listRoomCount = new ArrayList<RoomCount>();
		UserStatus userstatus = null;
		
		try {
			userstatus = getUserStatus(idUser);
			
		} catch (UserNotFoundException e) {
			
			return null;
		}
		
		for(Permission p : list) 
			for(Room r : roomList) 
				if(p.getIdRoom().equals(r.getId()))
					listRoomCount.add( new RoomCount(r));
			
		int k;
		
		for(RoomCount rc : listRoomCount) {
			
			k = 0;
			//count all user in this room
			for(UserStatus us : userStatusList) {
				if( us.getCurrentRoom() != null && rc.getId().equals(us.getCurrentRoom().getId()))
					k++;
			}
			
			rc.setCurrentPeople(k);
		}
		
		return new SynchStatus(userstatus, listRoomCount);
	}
	
	
	/// methods for client request
	

}
