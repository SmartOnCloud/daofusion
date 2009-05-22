package com.anasoft.os.daofusion.sample.hellodao.server;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anasoft.os.daofusion.sample.hellodao.server.db.DatabaseInitializer;

/**
 * Servlet that uses {@link DatabaseInitializer} to generate test data.
 */
public class DatabaseInitializationServlet extends HttpServlet {
    
    private static final long serialVersionUID = -221044081409609487L;
    
    private static final boolean DEFAULT_ENABLE_REINITIALIZATION = false;
    private static final int DEFAULT_NUMBER_OF_CUSTOMERS = 512;
    
    private boolean isInitialized = false;
    private boolean enableReinitialization = DEFAULT_ENABLE_REINITIALIZATION;
    
    private int numberOfCustomers = DEFAULT_NUMBER_OF_CUSTOMERS;
    
    private DatabaseInitializer initializer;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        String initParam = config.getInitParameter("enableReinitialization");
        if (initParam != null)
            enableReinitialization = Boolean.valueOf(initParam);
        
        initParam = config.getInitParameter("numberOfCustomers");
        if (initParam != null)
            numberOfCustomers = Integer.valueOf(initParam);
        
        initializer = new DatabaseInitializer(numberOfCustomers);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        if (!isInitialized || enableReinitialization) {
            initializer.clean();
            initializer.init();
            isInitialized = true;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }
    
}
