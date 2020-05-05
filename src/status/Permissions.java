package status;

import java.util.ArrayList;
import java.util.Iterator;

import basic.Permission;
import basic.Room;
import basic.User;
import exceptions.PermissionNotFoundException;
import model.Model;

public class Permissions{
	private ArrayList<Permission> permissionList;
	
	public Permissions(){
		permissionList = Model.loadPermissions();
	}
	
	public ArrayList<Permission> getList(){
		return permissionList;
	}
	
	private int findPermission(String idUser, String idRoom) throws PermissionNotFoundException {
		
		if(idUser == null || idRoom == null)
			throw new PermissionNotFoundException();
		
		for(int i = 0; i < permissionList.size(); i++)
			if(permissionList.get(i).getIdUser().equals(idUser) && permissionList.get(i).getIdRoom().equals(idRoom)) 
				return i;
		
		//if I cannot find it
		throw new PermissionNotFoundException();
				
	}	

	public Permission getPermission(String idUser, String idRoom) throws PermissionNotFoundException {
	
		return permissionList.get(findPermission(idUser, idRoom));
	}
	
	public ArrayList<Permission> findUserPermission(String idUser)
	{
		ArrayList<Permission> list = new ArrayList<Permission>();
		
		for(int i = 0; i < permissionList.size(); i++)
			if(permissionList.get(i).getIdUser().equals(idUser)) 
				list.add(permissionList.get(i));
		
		return list;
	}

	
	public boolean addNewPermission(Permission permission) {
		
		try {
			
			findPermission(permission.getIdUser(), permission.getIdRoom());
			
		} catch (PermissionNotFoundException e) {
			//if there is no user with that room permission
			
			if( Model.insertPermission(permission)) {
				permissionList.add(permission);
				return true;
			}
		}
		
		return false;
	}
	
	public void removePermission(String userId ,String roomId) throws PermissionNotFoundException {	
		
		permissionList.remove(findPermission(userId, roomId));
		Model.deletePermission(userId, roomId);
		
	}

	public ArrayList<String> getAccessableRooms(User u) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(int i = 0; i < permissionList.size(); i++)
			if(permissionList.get(i).getIdUser().equals(u.getId()))
				list.add(permissionList.get(i).getIdRoom());
		
		return list;
	}
	
	public ArrayList<String> getAllowedUsers(Room r) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(int i = 0; i < permissionList.size(); i++)
			if(permissionList.get(i).getIdRoom().equals(r.getId()))
				list.add(permissionList.get(i).getIdUser());
		
		return list;
	}

	public void removeAllPermissionsOfUser(String userId) {

		Iterator<Permission> it = permissionList.iterator();
		Permission current;
		
		while(it.hasNext()) {
			current = it.next();
			if(current.getIdUser().equals(userId))
				it.remove();
		}
		
	}

	public void removeAllPermissionsOfRoom(String roomId) {

		Iterator<Permission> it = permissionList.iterator();
		Permission current;
		
		while(it.hasNext()) {
			current = it.next();
			if(current.getIdRoom().equals(roomId))
				it.remove();
		}
			
		
	}


	
	

}