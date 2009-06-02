package com.anasoft.os.daofusion.test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * Utility class for managing an in-memory database instance.
 * 
 * <p>
 * 
 * Note that the database instance is created by loading
 * the JDBC driver class via the {@link Class#forName(String)}
 * method and destroyed by executing the <tt>SHUTDOWN</tt>
 * command.
 * 
 * @author vojtech.szocs
 */
public class InMemoryDatabaseManager {

    private static final String KEY_JDBC_DRIVER_CLASS = "dataSource.driverClass";
    private static final String KEY_CONNECTION_URL = "dataSource.jdbcUrl";
    private static final String KEY_USER_NAME = "dataSource.user";
    private static final String KEY_PASSWORD = "dataSource.password";
    
    private Properties beanSetupProperties;
    
    /**
     * Creates the database instance.
     * 
     * @throws Exception If anything goes wrong.
     */
    public void init() throws Exception {
        Class.forName(beanSetupProperties.getProperty(KEY_JDBC_DRIVER_CLASS));
    }
    
    /**
     * Destroys the database instance.
     * 
     * @throws Exception If anything goes wrong.
     */
    public void destroy() throws Exception {
        Connection connection = DriverManager.getConnection(
        		beanSetupProperties.getProperty(KEY_CONNECTION_URL),
        		beanSetupProperties.getProperty(KEY_USER_NAME),
        		beanSetupProperties.getProperty(KEY_PASSWORD));
        
        Statement statement = connection.createStatement();
        
        statement.execute("SHUTDOWN");
        connection.close();
    }
    
    public void setBeanSetupProperties(Properties beanSetupProperties) {
        this.beanSetupProperties = beanSetupProperties;
    }
    
}
