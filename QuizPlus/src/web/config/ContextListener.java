package web.config;

import java.sql.Connection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
	DataBaseConnection dbconn;
	Connection conn;

	
    public ContextListener() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void contextInitialized(ServletContextEvent event) {
	   	dbconn = new DataBaseConnection();
    	conn = dbconn.getCon();
    	event.getServletContext().setAttribute("Database", dbconn);
    	event.getServletContext().setAttribute("Connection", conn);

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		dbconn.destroy();
	}
	
}
