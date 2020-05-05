package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import basic.Permission;
import basic.Room;
import basic.User;

public class DataBase {
	
	private static String url = "jdbc:mysql://localhost:3306/bsm?serverTimezone=UTC";
	private static String user = "root";
	private static String password = "";
	
	private DataBase() {}
	
	/**
	 *	Connection Methods 
	 * 
	 */	
	public static Statement connect() {
		
		Connection connect;
		
		try {
			
			connect = DriverManager.getConnection(url, user, password);
			return connect.createStatement();
			
		} catch (SQLException e) {
			
			System.out.println("Connection Error!");
			System.exit(0);
			return null;
		}
		
	}
	
	public static void closeConnection(Statement st) {
		try {
			
			st.close();
			
		} catch (SQLException e) {
			
			System.out.println("Closing Connection Error!");
			System.exit(0);
		}
	}
	
	/**
	 *	SELECT Methods 
	 * 
	 */	
	public static ResultSet selectUsers(Statement st) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM user INNER JOIN login ON user.email = login.email");
			
		} catch (SQLException e) {
			System.out.println("Query -SelectUsers- Error!");
		}
		
		return result;
	}
	
	public static ResultSet showUsers(Statement st) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM user");
			
		} catch (SQLException e) {
			System.out.println("Query -SelectUsers- Error!");
		}
		
		return result;
	}
	
	public static ResultSet selectRooms(Statement st) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM room");
			
		} catch (SQLException e) {
			System.out.println("Query -SelectRooms- Error!");
		}
		
		return result;
	}
	
	public static ResultSet selectPermissionsByID(Statement st, String idUser) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT room.ID AS roomID FROM room INNER JOIN permission ON room.ID = permission.IDRoom WHERE permission.IDUser = " + idUser);
			
		} catch (SQLException e) {
			System.out.println("Query -SelectPermission- Error!");
		}
		
		return result;
	}
	
	
public static ResultSet showPermission(Statement st) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM permission");
			
		} catch (SQLException e) {
			System.out.println("Query -SelectPermission- Error!");
		}
		
		return result;
	}

	private static String selectIDbyemail(Statement st, String email) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT ID FROM user WHERE email = '" + email + "'");
			if(result.next())
				return result.getString("ID");
			
		} catch (SQLException e) {
			System.out.println("Query -SelectIdUser- Error!");
		}
		
		return null;		
		
	}
	
	private static String selectIDbyRoomName(Statement st, String roomName) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT ID FROM room WHERE name = '" + roomName + "'");
			if(result.next())
				return result.getString("ID");
			
		} catch (SQLException e) {
			System.out.println("Query -SelectIdRoom- Error!");
		}
		
		return null;		
		
	}
	
	
	public static String getHashedPassword(Statement st, String password) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT md5('" + password + "') AS hashedPassword");
			if(result.next())
				return result.getString("hashedPassword");
			
		} catch (SQLException e) {
			System.out.println("Query -hash password- Error!");
		}
		
		return null;		
		
	}
	
	/**
	 *	INSERT Methods 
	 * 
	 */	
	public static boolean insertNewUser(Statement st, User u, String password) {
		
		int result = 0;
		
		//login table
		String loginQuery = "INSERT INTO login(email, securityQuestion1, securityQuestion2, password) "
						+ 	"VALUES('" + u.getEmailId() + "','" + u.getSecurityQuestion1() + "','"+ u.getSecurityQuestion2() + "',"
						+	((password == null)?"null":"md5('" + password + "')") + ")";
		
		try {
			st.executeUpdate(loginQuery);
		} catch (SQLException e) {
			System.out.println("Can't add new login tuple." + loginQuery);
			return false;
		}
		
		//user table
		String userQuery = "INSERT INTO user(firstName, secondName, email, designation, department, mobileNumber, doB) "
						+ 	"VALUES('" + u.getFirstName() + "','" + u.getLastName() + "','" + u.getEmailId() + "','" 
							+ u.getDesignation() + "','" + u.getDepartment() + "','" + u.getMobileNumber() + "','" + u.getDateOfBirth() + "')";
		
		try {
			
			result = st.executeUpdate(userQuery);
			u.setId(selectIDbyemail(st, u.getEmailId()));
			
		} catch (SQLException e) {
			System.out.println("Problem with the query " + userQuery);
		}		
		
		return (result > 0);
	}
	
	public static boolean insertNewRoom(Statement st, Room r, String password) {
		
		int result = 0;
		
		String query = "INSERT INTO room(name, maxSize, password) "
					+ "VALUES ('" + r.getRoomName() + "'," + r.getMaxSize() + ","
					+ ((password == null)?"null":"md5('" + password + "')") + ")";
		
		try {
			
			result = st.executeUpdate(query);
			r.setId(selectIDbyRoomName(st, r.getRoomName()));
			
		} catch (SQLException e) {
		
		}
		
		return (result > 0);
	}
	
	public static boolean insertPermissions(Statement st, Permission p) {
		
		int result = 0;
		
		String query =	"INSERT INTO permission(IDUser, IDRoom) "
					+	"VALUES ('" + p.getIdUser() + "','" + p.getIdRoom() +"')";
		
		try {
			result = st.executeUpdate(query);
		} catch (SQLException e) {
		
		}
		
		return (result > 0);
	
	}
	
	/**
	 * DELETE Method
	 */
	public static boolean deleteUser(Statement st, String id) {
		
		int result = 0;
		
		try {
			result = st.executeUpdate("DELETE FROM user WHERE ID = '" + id + "'");
		} catch (SQLException e) {
			System.out.println("Query -DeleteUser- Error!");
		}
		
		return (result > 0);
	}
	
	public static boolean deleteRoom(Statement st, String id) {
		
		int result = 0;
		
		try {
			result = st.executeUpdate("DELETE FROM room WHERE ID = '" + id + "'");
		} catch (SQLException e) {
			System.out.println("Query -DeleteRoom- Error!");
		}
		
		return (result > 0);
	}
	
	public static boolean deletePermission(Statement st, String idUser, String idRoom) {
		
		int result = 0;
		
		try {
			
			result = st.executeUpdate("DELETE FROM permission WHERE IDUser = '" + idUser + "' AND IDRoom = '" + idRoom + "'");
			
		} catch (SQLException e) {
			System.out.println("Query -DeletePermission- Error!");
		}
		
		return (result > 0);
	}
	
	/**
	 * UPDATE Method
	 */
	public static boolean updateUserPassword(Statement st, String email, String password) {
		
		int result = 0;
		
		try {
			
			result = st.executeUpdate("UPDATE login SET password = "
									+ ((password == null)?"null":"md5('" + password + "')") + 
									" WHERE email = '" + email +"'");
			
		} catch (SQLException e) {
			System.out.println("Query -UpdateUserPassword- Error!");
		}
		
		return (result > 0);
	}
	
	public static boolean updateRoomPassword(Statement st, String idRoom, String password) {
		
		int result = 0;
		
		try {
			
			result = st.executeUpdate("UPDATE room SET password =" + 
									((password == null)?"null":"md5('" + password + "')") + 
									" WHERE ID = '" + idRoom +"'");
			
		} catch (SQLException e) {
			System.out.println("Query -UpdateUserPassword- Error!");
		}
		
		return (result > 0);
	}

	
   public static ResultSet userExist(Statement st, String permissionUserId) {
		
	   ResultSet result = null;
		
		try {
			
			result = st.executeQuery("SELECT * FROM user Where ID = '" + permissionUserId + "'");
			
		} catch (SQLException e) {
			System.out.println("Query -PermissionUser- Error!");
		}
		
		return result;
	}
   
   public static ResultSet roomExist(Statement st, String roomId) {
		
	   ResultSet result = null;
		
		try {
			
			result = st.executeQuery("SELECT * FROM room Where ID = '" + roomId + "'");
			
		} catch (SQLException e) {
			System.out.println("Query -RoomExist- Error!");
		}
		
		return result;
	}
   

   public static ResultSet duplicatePermission(Statement st, String permissionUserId, String permissionRoomId) {
		
		ResultSet result = null;
		
		try {
			result = st.executeQuery("SELECT * FROM permission Where IDUser = '" + permissionUserId + "' AND IDRoom = '" + permissionRoomId + "'");
			
		} catch (SQLException e) {
			System.out.println("Query -DuplicatePermission- Error!");
		}
		
		return result;
	}
   
   
   public static boolean deleteUserPermission(Statement st, String idUser) {
		
		int result = 0;
		
		try {
			
			result = st.executeUpdate("DELETE FROM permission WHERE IDUser = '" + idUser + "'");
			
		} catch (SQLException e) {
			System.out.println("Query -DeleteUserFromPermissionTable- Error!");
		}
		
		return (result > 0);
	}
   
   
   public static boolean deleteRoomPermission(Statement st, String idRoom) {
		
		int result = 0;
		
		try {
			
			result = st.executeUpdate("DELETE FROM permission WHERE IDRoom = '" + idRoom + "'");
			
		} catch (SQLException e) {
			System.out.println("Query -DeleteRoomFromPermissionTable- Error!");
		}
		
		return (result > 0);
	}
   
   
   public static boolean deleteLogin(Statement st, String email) {
		
		int result = 0;
		
		try {
			result = st.executeUpdate("DELETE FROM login WHERE email = '" + email + "'");
		} catch (SQLException e) {
			System.out.println("Query -Deletelogin- Error!");
		}
		
		return (result > 0);
	}


}
