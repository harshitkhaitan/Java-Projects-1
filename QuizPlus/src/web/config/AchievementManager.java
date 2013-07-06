package web.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import dbAccess.DBAccess;

public class AchievementManager {

	public static ArrayList<Achievement> getAchievements(String username, Connection con, DBAccess dbAccess) {
		ArrayList<Achievement> result = new ArrayList<Achievement>();
		
		
		if (AccountManager.userExists(username, con) == false) return null;
		AchievementManager.update(username,con,dbAccess);

		String query = "Select * FROM Achievements WHERE username='" + username + "'";
		System.out.println(query);

		try {
			ResultSet rs;
			PreparedStatement stmtPrep = con.prepareStatement(query);
			rs = stmtPrep.executeQuery();
			while (rs.next()) {
	            String achievement = rs.getString("achievement");
	            int type = rs.getInt("achievement_type");
	            Timestamp timest = rs.getTimestamp("Mtime");
	            result.add(new Achievement(achievement,timest,type));  	    			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


		
		
		return result;
	}

	private static void update(String username, Connection con, DBAccess dbAccess) {
		int total_quiz_created = dbAccess.totalQuizCreated(username);
		if(total_quiz_created>0){
			addAchievement(username, 1, con);
			if(total_quiz_created>=5){
				addAchievement(username, 2, con);
				if(total_quiz_created>=10){
					addAchievement(username, 3, con);
				}
			}	
		}
		
		int total_quiz_taken = dbAccess.totalQuizTaken(username);
		if(total_quiz_taken>=10) addAchievement(username, 4, con);
		
		int total_practice_quiz_taken = dbAccess.totalPracticeQuizTaken(username);
		if(total_practice_quiz_taken>0) addAchievement(username, 6, con);		
		
		boolean highest_score_true = dbAccess.highestScoreInATest(username);
		if(highest_score_true) addAchievement(username, 5, con);
			
	}

	public static void addAchievement(String username, int type, Connection con) {
		
		String achievement_name; 
		
		switch (type) {
        case 1:  achievement_name = "Amateur Author";
                 break;
        case 2:  achievement_name = "Prolific Author";
                 break;
        case 3:  achievement_name = "Prodigious Author";
                 break;
        case 4:  achievement_name = "Quiz Machine";
                 break;
        case 5:  achievement_name = "I am the Greatest";
                 break;
        case 6:  achievement_name = "Practice Makes Perfect";
                 break;
        case 7:  achievement_name = "Social Animal";
                 break;
        default: achievement_name = "Invalid";
                 break;
		}
		
		if(achievement_name.equals("Invalid")) return;
		
		if (AccountManager.userExists(username, con) == false) return;
		if (checkAchievement(username, type, con) == true) return;
			
		// 	INSERT INTO Achievements (`username`, `achievement`) VALUES ('harshit1', 'bogus');
		String query = "Insert INTO Achievements (`username`, `achievement`,`achievement_type`) VALUES ('" + username + "','" + achievement_name + "','" + type + "')" ;
		System.out.println(query);

		try {
			PreparedStatement stmtPrep = con.prepareStatement(query);
			stmtPrep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
		public static boolean checkAchievement(String username, int type, Connection con) {

			if (AccountManager.userExists(username, con) == false) return false;

			String query = "Select * FROM Achievements WHERE username='" + username + "' AND achievement_type='" + type + "'";
			System.out.println(query);

			try {
				ResultSet rs;
				PreparedStatement stmtPrep = con.prepareStatement(query);
				rs = stmtPrep.executeQuery();
				while (rs.next()) {
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			
			
			return false;
		}


	
}
