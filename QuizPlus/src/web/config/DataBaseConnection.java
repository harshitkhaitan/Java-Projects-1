package web.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dbAccess.DBAccess;


public class DataBaseConnection {
	

	private static String account = "ccs108vigupta";
	private static String db_password = "eephohsh";
	private static String server = "mysql-user.stanford.edu";
	private static String database = "c_cs108_vigupta";

	
	
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private Object UserTableLock;
	

	public DataBaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection( "jdbc:mysql://" + server, account, db_password);

			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			rs = stmt.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		UserTableLock = new Object();

	}


	public Connection getCon() {
		return con;
	}
	
	public void destroy() {
		try {
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public boolean CheckAccount(String userName, String password) {
		boolean result = false;
		synchronized (UserTableLock) {
			result = AccountManager.checkConnection(userName, password, con);
		}
		return result;
	}
	
	public boolean CreateAccount(String userName, String password) {
		boolean result = false;
		synchronized (UserTableLock) {
			result = AccountManager.createConnection(userName, password, con);
		}
		return result;
	}


	public boolean SendMessage(String fromUser, String toUser, String message, int type, String quiz_name, int bestScore) {
		boolean result = false;
		synchronized (UserTableLock) {
			result = MessageManager.sendMessage(fromUser, toUser, message, type, quiz_name, bestScore, con);
		}
		return result;
	}
	
	public ArrayList<Message> getMyinbox(String fromUser, int type){
		synchronized (UserTableLock) {
			MessageManager mgr = new MessageManager();
			return mgr.getMessages(fromUser, con, type);
		}
	}

	public Message getMyMessage(String toUser, int ID){
		synchronized (UserTableLock) {
			return MessageManager.getMessage(toUser, con, ID);
		}
	}

	public void acceptFriendRequest(String fromUser, String toUser) {
		synchronized (UserTableLock) {
			FriendManager.acceptRequest(fromUser, toUser, con);
			addAchievements(fromUser, 7);
			addAchievements(toUser, 7);
		}
	}


	public void deleteFriendRequest(String toUser, String ID) {
		synchronized (UserTableLock) {
			MessageManager.deleteMessage(toUser, con, ID);
		}
	}
	
	public boolean checkIfExists(String user){
		boolean result = false;
		synchronized (UserTableLock) {
			result = AccountManager.userExists(user, con);
		}
		return result;
	}
	
	public ArrayList<Friend> getFriends(String user){
		synchronized (UserTableLock) {
			FriendManager fmgr = new FriendManager(user, con);
			if(!fmgr.getFriends().isEmpty()) addAchievements(user, 7);
			return fmgr.getFriends();
		}
	}
	
	public void removeFriend(String userName, String friendName){
		synchronized (UserTableLock) {
			FriendManager.removeFriend(userName, friendName, con);
			FriendManager.removeFriend(friendName, userName, con);
		}
	}
	
	public ArrayList<Achievement> getUserAchievements(String username, DBAccess dbAccess){
		ArrayList<Achievement> result = null;
			result = AchievementManager.getAchievements(username, con, dbAccess);
		return result;
	}
	
	public void addAchievements(String username, int type){
			AchievementManager.addAchievement(username, type, con);
	}

}
