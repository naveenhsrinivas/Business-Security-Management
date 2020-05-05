package status;

import java.util.ArrayList;

import basic.Room;
import basic.User;

public class RoomStatus {
	
	private Room r;
	private ArrayList<User> listUser;
	
	public RoomStatus(Room r, ArrayList<User> listUser) {
		this.r = r;
		this.listUser = listUser;
	}
	
	public RoomStatus(Room r) {
		this.r = r;
		this.listUser = new ArrayList<User>();
	}
	
	public Room getRoom() {
		return r;
	}
	
	public int peopleInside() {
		return listUser.size();
	}
	
	public void add(User newUser) {
		listUser.add(newUser);
	}
	
	public ArrayList<User> getUsers(){
		return listUser;
	}
	
	public void print() {
		
		System.out.println("In room '" + r.getRoomName() + "' (" + listUser.size() + "/" + r.getMaxSize() + "):");
		
		if(peopleInside() == 0)
			System.out.println();
		
		for(int i = 0; i < listUser.size(); i++)
			System.out.println(listUser.get(i).getName());
		
	}

	public boolean isFull() {
		return (peopleInside() >= r.getMaxSize());
	}

}
