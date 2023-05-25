package AllServletOperations;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBclass {
	Connection connection=null;
	private static final Logger log = LogManager.getLogger(DBclass.class);

	public Connection getConnection() {
		try {
		
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			DataSource dataSource = (DataSource)envContext.lookup("jdbc/newemployedata");
			try {
				connection= dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("src/main/java/AllServletOperations/DBclass.java : "+e.getMessage()+" SQLException Error");
			}
			return connection;

		} catch (NamingException e) {
			e.printStackTrace();
			log.error("src/main/java/AllServletOperations/DBclass.java : "+e.getMessage()+" NamingException Error");
		}
		return connection ;
		
	}

}

