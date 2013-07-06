package web.config;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountManager {
	
	public static boolean checkConnection(String userName, String password, Connection conn) {
		
		String salt = "";
		String hashedPassword = "";
		String GeneratedhashedPassword = "";		
		ResultSet rs;
		try {
			String query = "Select * FROM userAccounts WHERE username='" + userName + "'";
			PreparedStatement stmtPrep = conn.prepareStatement(query);
			rs = stmtPrep.executeQuery();
		      while (rs.next()) {
		            salt = rs.getString("salt");
		            hashedPassword = rs.getString("password");
		      }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (hashedPassword.equals("")) return false;
		
		GeneratedhashedPassword = hashIt(password, salt);
		if (hashedPassword.equals(GeneratedhashedPassword)) return true;
				
		return false;
	}
	
	private static String hashIt(String password, String salt){
		byte[] resultByte = Generator(password.getBytes());
		String GeneratedhashedPassword = hexToString(resultByte);; 
		String l2_password = salt + GeneratedhashedPassword;
		byte[] l2_resultByte = Generator(l2_password.getBytes());
		String l2_GeneratedhashedPassword = hexToString(l2_resultByte);
		return l2_GeneratedhashedPassword;
	}

	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	private static byte[] Generator(byte[] password) {
		byte[] hashedPassword;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(password);
			hashedPassword = md.digest();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return hashedPassword;
	}

	public static boolean createConnection(String userName, String password, Connection con) {

		ResultSet rs;
		try {
			String query = "Select * FROM userAccounts WHERE username='" + userName + "'";
			PreparedStatement stmtPrep = con.prepareStatement(query);
			rs = stmtPrep.executeQuery();
			if (rs.next()) return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SecureRandom random = new SecureRandom();		
		String salt = new BigInteger(130, random).toString(32);
		String GeneratedhashedPassword = "";		
		GeneratedhashedPassword = hashIt(password, salt);
		try {
//			INSERT INTO `c_cs108_hkhaitan`.`userAccounts` (`username`, `password`, `salt`) VALUES ('vigupta', '1234', '1234');
			String fields = "('" + userName + "','" + GeneratedhashedPassword + "','" + salt + "')" ;
			String query = "INSERT INTO userAccounts (`username`, `password`, `salt`) VALUES " + fields;
			System.out.println(query);
			PreparedStatement stmtPrep = con.prepareStatement(query);
			stmtPrep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		
		
		return true;
	}

	public static boolean userExists(String userName, Connection con) {
		ResultSet rs;
		String query = "Select * FROM userAccounts WHERE username='" + userName + "' COLLATE latin1_general_cs";
		System.out.println(query);
		try {
			PreparedStatement stmtPrep = con.prepareStatement(query);
			rs = stmtPrep.executeQuery();
			if (rs.next()) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
}
