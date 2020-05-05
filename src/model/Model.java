package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import basic.*;

public class Model {
	
	private Model() {}
	
	public static boolean insertNewUser(User newUser, String password) {
		
		Statement st = DataBase.connect();
		
		boolean result = DataBase.insertNewUser(st, newUser, password);
		
		DataBase.closeConnection(st);
		
		return result;
		
	}
	
	public static boolean insertNewRoom(Room newRoom, String password) {
		
		Statement st = DataBase.connect();
		
		boolean result = DataBase.insertNewRoom(st, newRoom, password);
		
		DataBase.closeConnection(st);
		
		return result;
		
	}

	public static boolean insertPermission(Permission newPermission) {
		
		Statement st = DataBase.connect();
		
		boolean result = DataBase.insertPermissions(st, newPermission);
		
		DataBase.closeConnection(st);
		
		return result;
		
	}
	
	public static ArrayList<User> loadUsers(){
		
		ArrayList<User> list = new ArrayList<User>();
		
		Statement st = DataBase.connect();
		
		ResultSet resSet = DataBase.selectUsers(st);
		
		try {
			
			while(resSet.next()) {
				
				User newUser = new User();
				
				newUser.setId(resSet.getString("ID"));
				newUser.setFirstName(resSet.getString("firstName"));
				newUser.setLastName(resSet.getString("secondName"));
				newUser.setEmailId(resSet.getString("email"));
				newUser.setDesignation(resSet.getString("designation"));
				newUser.setDepartment(resSet.getString("department"));
				newUser.setMobileNumber(resSet.getString("mobileNumber"));
				newUser.setDateOfBirth(resSet.getString("doB"));
				newUser.setPassword(resSet.getString("password"));
				newUser.setSecurityQuestion1(resSet.getString("securityQuestion1"));
				newUser.setSecurityQuestion2(resSet.getString("securityQuestion2"));
				
				list.add(newUser);
				
			}
			
		} catch (SQLException e) {
			System.out.println("Problem reading users!");
		}
		
		DataBase.closeConnection(st);
		
		return list;		
		
	}
	
	public static ArrayList<Room> loadRooms(){
		
		ArrayList<Room> list = new ArrayList<Room>();
		
		Statement st = DataBase.connect();
		
		ResultSet resSet = DataBase.selectRooms(st);
		
		try {
			while(resSet.next()) {
				
				Room newRoom = new Room();
				
				newRoom.setId(resSet.getString("ID"));
				newRoom.setRoomName(resSet.getString("Name"));
				newRoom.setMaxSize(resSet.getInt("maxSize"));
				newRoom.setRoomPassword(resSet.getString("password"));
				
				list.add(newRoom);			
			}
		} catch (SQLException e) {
			System.out.println("Problem reading rooms!");
		}
		
		DataBase.closeConnection(st);
		
		return list;		
		
	}
	
	public static ArrayList<Permission> loadPermissions(){
		
		ArrayList<Permission> list = new ArrayList<Permission>();
		
		Statement st = DataBase.connect();
		
		ResultSet resSet = DataBase.showPermission(st);
		
		try {
			while(resSet.next()) {
				
				Permission newPermission = new Permission(resSet.getString("IDUser"),resSet.getString("IDRoom"));	
				list.add(newPermission);			
			}
		} catch (SQLException e) {
			System.out.println("Problem reading permissions!");
		}
		
		DataBase.closeConnection(st);
		
		return list;		
		
	}

	public static void deleteRoom(String idRoom) {
		
		Statement st = DataBase.connect();
		
		DataBase.deleteRoom(st, idRoom);
		
		DataBase.closeConnection(st);
		
	}
	
	public static void deleteUser(String idUser) {
		
		Statement st = DataBase.connect();
		
		DataBase.deleteUser(st, idUser);
		
		DataBase.closeConnection(st);
		
	}
	
	public static void deletePermission(String idUser, String idRoom) {
		
		Statement st = DataBase.connect();
		
		DataBase.deletePermission(st, idUser, idRoom);
		
		DataBase.closeConnection(st);
		
	}

	public static String getHashedPassword(String password) {
		
		Statement st = DataBase.connect();
		
		String hashedPassword = DataBase.getHashedPassword(st, password);
		
		DataBase.closeConnection(st);
		
		return hashedPassword;
	}
	
	public static boolean updateUserPassword(String email, String password) {
		
		Statement st = DataBase.connect();
		
		boolean result = DataBase.updateUserPassword(st, email, password);
		
		DataBase.closeConnection(st);
		
		return result;
		
	}
	
	public static boolean updateRoomPassword(String idRoom, String password) {
		
		Statement st = DataBase.connect();
		
		boolean result = DataBase.updateRoomPassword(st, idRoom, password);
		
		DataBase.closeConnection(st);
		
		return result;
		
	}
	

}
