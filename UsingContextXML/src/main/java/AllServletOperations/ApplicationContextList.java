package AllServletOperations;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



/**
 * Application Lifecycle Listener implementation class ApplicationContextList
 *
 */
public class ApplicationContextList implements ServletContextListener {


    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext cxt = sce.getServletContext();
    	DBclass dbclass = new DBclass();
    	cxt.setAttribute("EmployeeDatabase", dbclass);

    	
    }
	
}
