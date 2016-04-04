package pb.repo.common.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

/*
 * This class used by Scheduler classes
 */
public class JdbcConnectionFactory {
	
	private static Logger log = Logger.getLogger(JdbcConnectionFactory.class);
	
    private static Properties properties;
    
    static {
    	 
        try {
            properties = new Properties();
        }
        catch (Exception ex) {
            log.error("", ex);
        }
    }

	public static Connection getConnection() throws Exception {
		
		Connection connection = null;
		
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("alfresco-global.properties");
			properties.load(inputStream);
	
			String dbDriver = properties.getProperty("db.driver");
			Class.forName(dbDriver).newInstance();
	
			String dbName = properties.getProperty("db.name");
			String dbURL = properties.getProperty("db.url");
			dbURL = dbURL.replaceAll("\\$\\{db.name\\}", dbName);
	
			String dbUserName = properties.getProperty("db.username");
			String dbPassword = properties.getProperty("db.password");
			
			connection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
			
		} catch (SQLException ex) {
			log.error("connectDB : error : ", ex);
		}

		return connection;
	}
}
