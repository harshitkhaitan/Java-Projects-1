package web.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendManager {
	
	private ArrayList<Friend> friends;
	
	public FriendManager(String user, Connection con){
		friends = new ArrayList<Friend>();
		populateFriendList(user, con);
	}

	public void populateFriendList(String user, Connection con){
		if (AccountManager.userExists(user, con) == false) return;		
		ResultSet rs;
		String query = "Select * FROM friends WHERE username1='" + user + "'";
		System.out.println(query);
		try {
			PreparedStatement stmtPrep = con.prepareStatement(query);
			rs = stmtPrep.executeQuery();
			String friendName;
			while (rs.next()){
				friendName = rs.getString("username2");
				System.out.println(friendName);
				friends.add(new Friend(friendName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}
	
	public ArrayList<Friend> getFriends() {
		return friends;
	}

	public static void acceptRequest(String fromUser, String toUser,Connection con) {
		
		if (AccountManager.userExists(toUser, con) == false) return;
		if (AccountManager.userExists(fromUser, con) == false) return;
		
		if(FriendManager.checkFriends(toUser,fromUser, con) == true) return;
		
		insertRequest(fromUser, toUser, con);
		insertRequest(toUser, fromUser, con);

		return ;
		
	}
	
	public static void insertRequest(String fromUser, String toUser,Connection con){
		try {
//			INSERT INTO Messages (`fromUser`, `toUser`, `message`, `Mtype`) VALUES ('harshit1', 'harshit2', '1234', '0');
			String fields = "('" + fromUser + "','" + toUser + "')" ;
			String query = "INSERT INTO friends (`username1`, `username2`) VALUES " + fields;
			System.out.println(query);
			PreparedStatement stmtPrep = con.prepareStatement(query);
			stmtPrep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return ;
		}
	}

	private static boolean checkFriends(String toUser, String fromUser, Connection con) {

		if(check(toUser, fromUser, con)) return true;
		if(check(fromUser, toUser, con)) return true;
		
		return false;
	}

	private static boolean check(String user1, String user2, Connection con){
		ResultSet rs;
		String query = "Select * FROM friends WHERE username1='" + user1 + "'";
		System.out.println(query);
		try {
			PreparedStatement stmtPrep = con.prepareStatement(query);
			rs = stmtPrep.executeQuery();
			while (rs.next()){
				if(rs.getString("username2").equals(user2)) return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static void removeFriend(String userName, String friendName,	Connection con) {
		if (AccountManager.userExists(userName, con) == false) return;
		if (AccountManager.userExists(friendName, con) == false) return;
		try {
			// DELETE from Friends where username2="harshit3";
			String fields = "username1=\"" + userName + "\" AND username2=\"" + friendName + "\"" ;
			String query = "Delete from friends Where " + fields;
			System.out.println(query);
			PreparedStatement stmtPrep = con.prepareStatement(query);
			stmtPrep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}		
	}
	
	
	
}
