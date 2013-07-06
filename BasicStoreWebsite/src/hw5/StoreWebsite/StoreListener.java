package hw5.StoreWebsite;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.sql.Connection;

/**
 * Application Lifecycle Listener implementation class StoreListener
 *
 */
@WebListener
public class StoreListener implements ServletContextListener {

	DBConnection dbconn;
	Connection conn;
//	ProductList prods;
    /**
     * Default constructor. 
     */
    public StoreListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	dbconn = new DBConnection();
//    	event.getServletContext().setAttribute("DB Connection", dbconn);
    	Connection conn = dbconn.getCon();
    	event.getServletContext().setAttribute("Connection", conn);
//    	ProductList prods = dbconn.GetAllEntries();
//    	event.getServletContext().setAttribute("Product List", prods);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	dbconn.destroy();
    }
	
}
