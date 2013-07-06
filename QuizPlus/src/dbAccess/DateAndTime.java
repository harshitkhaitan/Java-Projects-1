package dbAccess;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class creates the date to be used for mysql timestamp 
 * 
 * @author jayakarr
 *
 */

public  class DateAndTime {
	
	Date date;
	
	/*
	 * Constructor
	 */
	DateAndTime() {
		this.date = new Date();
	}
	
	public DateAndTime(Date date1) {
		this.date = date1 ;
	}
	
	public Date getDateAndTime() {
		return date;
	}
	
	public  String dateForMySQL (){
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createDate = dateFormat1.format(date);
		return createDate;
		
	}
	
	public String getDate () {
		
		DateFormat dateFormat1 = new SimpleDateFormat("MM-dd-yyyy");
		String createDate = dateFormat1.format(date);
		
		return createDate;
		
	}
	
	public String getTime () {
		
		DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
		String createTime = dateFormat2.format(date);
		
		
		
		return createTime;
		
	}
	
}
