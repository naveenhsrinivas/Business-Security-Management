package server;

import basic.Room;
import basic.User;
import exceptions.PermissionNotFoundException;
import exceptions.RoomNotFoundException;
import exceptions.UserNotFoundException;
import status.RoomStatus;
import status.Status;
import status.UserStatus;
import view.AddView;
import view.BasicView;
import view.MenuView;
import view.SelectView;
import view.ShowView;

public class Admin{
	
	private Status status;
	
	public Admin(Status status) {
		this.status = status;
	}
	
	public void serverStatus() {
		ShowView.showServerStatus(status);
	}
	
	public void add(int choice) {
		
		boolean outcome;
		
		switch (choice) {
		
			case 1:
				User user = AddView.newUser();
				outcome = status.addNewUser(user, user.getPassword());
				BasicView.confirmOrAbort(outcome);
				break;
			
			case 2:
				Room room = AddView.newRoom();
				outcome = status.addNewRoom(room, room.getRoomPassword());
				BasicView.confirmOrAbort(outcome);
				break;
			
			case 3:
				String idUser = SelectView.selectUser(status.getAllUsers());
				String idRoom = SelectView.selectRoom(status.getAllRoomsStatus());
				outcome = status.addNewPermission(idUser, idRoom);
				BasicView.confirmOrAbort(outcome);
				break;

		default:
			break;
		}
		
		
	}
	
	public void remove(int choice) {
		
		String idUser, idRoom;
		
		switch (choice) {
		
			case 1:
				idUser = SelectView.selectUser(status.getAllUsers());
				try {
					
					UserStatus us = status.getUserStatus(idUser);
					
					if(us.isLogged()) // if he's logged, can't delete
						BasicView.cantCompleteOperation("The selected user is still logged in");
					else {
						status.removeUser(idUser);
						BasicView.confirmOrAbort(true);
					}
				
				} catch (UserNotFoundException e) {
					BasicView.cantCompleteOperation("The selected user doesn't exists");
				}
				break;
			
			case 2:
				idRoom = SelectView.selectRoom(status.getAllRoomsStatus());
				try {
					
					RoomStatus rs = status.getRoomStatus(idRoom);
					
					if(rs.peopleInside() > 0) // if there's someone in the room, can't delete
						BasicView.cantCompleteOperation("The selected room is not empty");
					else {
						status.removeRoom(idRoom);
						BasicView.confirmOrAbort(true);
					}
					
				} catch (RoomNotFoundException e) {
					BasicView.cantCompleteOperation("The selected room doesn't exists");
				}
			
				break;
			
			case 3:
				ShowView.showAllPermissions(status);
				idUser = SelectView.selectUser(status.getAllUsers());
				idRoom = SelectView.selectRoom(status.getAllRoomsStatus());
				
				try {
					
					UserStatus us = status.getUserStatus(idUser);
					RoomStatus rs = status.getRoomStatus(idRoom);
					
					if(us.getCurrentRoom() != null && us.getCurrentRoom() == rs.getRoom()) //if the user is in the room of the permission, can't delete
						BasicView.cantCompleteOperation("The selected permission is in use");
					else {
						status.removePermission(idUser, idRoom);
					}
					
				} catch (UserNotFoundException e) {
					BasicView.cantCompleteOperation("The selected user is not found");
				} catch (RoomNotFoundException e) {
					BasicView.cantCompleteOperation("The selected room is not found");
				} catch (PermissionNotFoundException e) {
					BasicView.cantCompleteOperation("The selected permission doesn't exist");
				}
				
				break;
	
		default:
			break;
		}
		
	}
	
	public void show(int choice) {
		
		switch (choice) {
			case 1:
				String idUser = SelectView.selectUser(status.getAllUsers());
				
				try {
					User u = status.getUserStatus(idUser).getUser();
					ShowView.showUser(u, status.permitted(u));
					
				} catch (UserNotFoundException e) {
					BasicView.cantCompleteOperation("The selected user is not found");
				}
				
				break;
			
			case 2:
				String idRoom = SelectView.selectRoom(status.getAllRoomsStatus());
				
				try {
					Room r = status.getRoom(idRoom);
					ShowView.showRoom(r, status.permitted(r));
					
				} catch (RoomNotFoundException e) {
					BasicView.cantCompleteOperation("The selected room is not found");
				}
				
				break;
			
			case 3:
				ShowView.showAllPermissions(status);
				break;
	
			default:
				break;
		}
		
	}
	
	public void manageRoom() {
		
		String idRoom = SelectView.selectRoom(status.getAllRoomsStatus());
		boolean outcome;
		
		try {
			Room r = status.getRoom(idRoom);
			
			if(r.hasPassword()) {
				
				int choice = MenuView.hasRoomPassword();
				
				switch (choice) {
				
					case 1:
						outcome = status.updateRoomPassword(idRoom, null);
						BasicView.confirmOrAbort(outcome);
						break;
						
					case 2:
						String newPassword = BasicView.getString("Insert the new password: ");
						outcome = status.updateRoomPassword(idRoom, newPassword);
						BasicView.confirmOrAbort(outcome);
						break;
	
					default:
						break;
				}	
				
			}
			else {
				
				int choice = MenuView.hasNoRoomPassword();
				
				switch (choice) {
				
					case 1:
						String newPassword = BasicView.getString("Insert the new password: ");
						outcome = status.updateRoomPassword(idRoom, newPassword);
						BasicView.confirmOrAbort(outcome);
						break;
	
					default:
						break;
				}
			}

			
		} catch (RoomNotFoundException e) {
			BasicView.cantCompleteOperation("The selected room is not found");
		}
		
		
	}
	
	
	
	

}
