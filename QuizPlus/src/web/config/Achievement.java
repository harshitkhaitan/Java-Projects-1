package web.config;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Achievement{
	
	private String achievement;
	private Timestamp timest;
	private int type;
	private String url;

	public Achievement(String achievement, Timestamp timest, int type) {
		this.achievement =achievement;
		this.timest = timest;
		this.type = type;
		switch (type) {
        case 1:  url = "http://farm4.static.flickr.com/3252/buddyicons/30559437@N03.jpg?1272043683";
                 break;
        case 2:  url = "http://www.hairlosshelp.com/forums/i/authorsicons/cop.gif";
                 break;
        case 3:  url = "http://4.bp.blogspot.com/_cI5NvkTlGZ4/SfV3FpyyUrI/AAAAAAAAAMI/4fKtlYU8SZc/s320/rss.png";
                 break;
        case 4:  url = "http://www.service-1.org/uploads/201108/10/imgs/transformer-toy-128_128x128.jpg";
                 break;
        case 5:  url = "http://www.veryicon.com/icon/png/Game/Mega%20Games%20Pack%2022/The%20Incredible%20Hulk%202.png";
                 break;
        case 6:  url = "http://www.sireasgallery.com/iconset/avengers/Captain-America_256x256_32.png";
                 break;
        case 7:  url = "http://findicons.com/files//icons/2067/twitter_blog/128/twitter2_512.png";
                 break;
        default: url = "http://iconbug.com/data/8d/256/2aa7d32f69a05f71140930b61f9299ad.png";
                 break;
		}
	}

	public static String toHtmlTable (ArrayList<Achievement> list, String opt) {
		String rv = String.format("<table class=\"list\" %s>\n", opt);
		for(int i = 0; i < list.size(); i++) {
			rv += "<tr>";
			rv += list.get(i).toHtmlTable();
			rv += "\n";
			}
		rv += "</table>";
	return rv; 	}
	
	
	public String toHtmlTable () {

		String rv = "";




		rv += "<td>";
		
		rv += "<img src=\"" + url + "\" alt=\"Smiley face\" height=\"40\" width=\"40\" />";

		
		rv += "<td>";

		rv += String.format("%s", achievement);

		
//		rv += "<td>";
//
//		rv += String.format("%s", timest);

				

		

		return rv;

	}

	
}
