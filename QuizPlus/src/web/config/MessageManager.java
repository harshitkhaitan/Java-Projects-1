package web.config;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

public class MessageManager {

	private ArrayList<Message> Messages;
	
	public MessageManager(){
		Messages = new ArrayList<Message>();
	}
		
	public ArrayList<Message> getMessages(String userName, Connection con, int type) {
		PopulateMessages(userName, con, type);
		return Messages;
	}
	
	private void PopulateMessages(String userName, Connection con, int type){
		if (AccountManager.userExists(userName, con) == false) return;
		ResultSet rs;
		String query = "Select * FROM Messages WHERE toUser='" + userName + "' ORDER BY Mtime DESC";
		System.out.println(query);
		try {
			PreparedStatement stmtPrep = con.prepareStatement(query);
			rs = stmtPrep.executeQuery();
			while (rs.next()) {
	            String fromName = rs.getString("fromUser");
	            String message = rs.getString("message");
	            int mtype = rs.getInt("Mtype");
	            int read = rs.getInt("Mread");
	            int id = rs.getInt("id");
	            Timestamp timest = rs.getTimestamp("Mtime");
	            String quizName = rs.getString("quizName");
	            int myScore = rs.getInt("myScore");
//	            System.out.println("Mesage From:" + fromName + " Message Type " + mtype);
	    		if (type>=0){
		            if(type == mtype) this.Messages.add(new Message(fromName, userName, message, mtype, read, id, timest, quizName, myScore));	    			
	    		}else{
		            this.Messages.add(new Message(fromName, userName, message, mtype, read, id, timest, quizName, myScore));	    			
	    		}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean sendMessage(String fromUser, String toUser, String message, int type, String quiz_name, int bestScore, Connection con) {
		
		if (AccountManager.userExists(toUser, con) == false) return false;
		if(quiz_name==null) quiz_name="";
		message = appendDeLimiters(message);
		quiz_name = appendDeLimiters(quiz_name);
		
		try {
//			INSERT INTO Messages (`fromUser`, `toUser`, `message`, `Mtype`) VALUES ('harshit1', 'harshit2', '1234', '0');
			String fields = "('" + fromUser + "','" + toUser + "','" + message + "','" + type + "','" + quiz_name + "','" + bestScore + "')" ;
			String query = "INSERT INTO Messages (`fromUser`, `toUser`, `message`, `Mtype`, `quizName`, `myScore`) VALUES " + fields;
			System.out.println(query);
			PreparedStatement stmtPrep = con.prepareStatement(query);
			stmtPrep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private static String appendDeLimiters(String str) {

		boolean DEBUG = true;
		int index = str.indexOf('\'');   

		if (DEBUG) System.out.println("String before Inserting =" + str);

		if (index > -1) {

			if (DEBUG) System.out.println("found a \' at position =" + index);

			str = str.substring(0, index) + "\\" + str.substring(index, str.length());


			str = str.substring(0, index+2) + appendDeLimiters(str.substring(index+2));
		}

		
		if (DEBUG) System.out.println("String After Inserting =" + str);
	
		return str;

	}

	public static Message getMessage(String toUser, Connection con, int iD) {
		if (AccountManager.userExists(toUser, con) == false) return null;

		String query = "Select * FROM Messages WHERE id='" + iD + "'";
		String query_read = "UPDATE Messages SET Mread=1 WHERE id='" + iD + "'";
		System.out.println(query);
		System.out.println(query_read);

		try {
			ResultSet rs;
			PreparedStatement stmtPrep_read = con.prepareStatement(query_read);
			PreparedStatement stmtPrep = con.prepareStatement(query);
			stmtPrep_read.executeUpdate();
			rs = stmtPrep.executeQuery();
			while (rs.next()) {
	            String fromName = rs.getString("fromUser");
	            String message = rs.getString("message");
	            int mtype = rs.getInt("Mtype");
	            int read = rs.getInt("Mread");
	            String toName = rs.getString("toUser");
	            Timestamp timest = rs.getTimestamp("Mtime");
	            String quizName = rs.getString("quizName");
	            int myScore = rs.getInt("myScore");
	    		if (toUser.equals(toName)){
		            return (new Message(fromName, toName, message, mtype, read, iD, timest, quizName, myScore));	    			
	    		}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void deleteMessage(String toUser, Connection con, String iD) {
		if (AccountManager.userExists(toUser, con) == false) return;
		
		try {
			// DELETE from Messages where id=25 AND toUser="harshit3";
			String fields = "id=" + iD + " AND toUser=\"" + toUser + "\"" ;
			String query = "Delete from Messages Where " + fields;
			System.out.println(query);
			PreparedStatement stmtPrep = con.prepareStatement(query);
			stmtPrep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		return;
	}
	

}
