package view;

import java.util.ArrayList;

import basic.RoomCount;
import basic.User;
import status.RoomStatus;

public class SelectView{
	
	private SelectView() {}
	
	public static String selectUser(ArrayList<User> userList) {
		
		System.out.println("Select User.");
		
		for(int i = 0; i < userList.size(); i++) 
			System.out.println(userList.get(i).getId() + ") "
					+ userList.get(i).getName() + " - " + userList.get(i).getDepartment()
					+ " - " + userList.get(i).getDesignation());
		
		return BasicView.askForId();		
		
	}
	
	public static String selectRoom(ArrayList<RoomStatus> roomStatusList) {
		
		System.out.println("Select Room.");
		
		for(int i = 0; i < roomStatusList.size(); i++) 
			System.out.println(roomStatusList.get(i).getRoom().getId() + ") "
					+ roomStatusList.get(i).getRoom().getRoomName() + 
					"(" + roomStatusList.get(i).peopleInside() + "/" + roomStatusList.get(i).getRoom().getMaxSize() + ")");
			
		return BasicView.askForId();		
		
	}
	
	public static String selectRoomClient(ArrayList<RoomCount> roomCountList) {
		
		System.out.println("Select the Room you want to enter.");
		
		for(int i = 0; i < roomCountList.size(); i++) 
			System.out.println(roomCountList.get(i).getId() + ") "
					+ roomCountList.get(i).getRoomName() + 
					"(" + roomCountList.get(i).getCurrentPeople() + "/" + roomCountList.get(i).getMaxSize() + ")");
			
		return BasicView.askForId();		
		
	}
	

}
