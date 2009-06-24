package com.anasoft.os.daofusion.sample.hellodao.server;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.ejb.HibernateEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anasoft.os.daofusion.sample.hellodao.server.dao.impl.EntityManagerHolder;

/**
 * Simple implementation of the <em>Open EntityManager In View</em>
 * pattern.
 * <p>
 * We recommend using Spring's <tt>OpenEntityManagerInViewFilter</tt>
 * in serious applications.
 * <p>
 * <b>Important notice:</b> This filter provides a single transaction
 * for the entire request processing ({@link FilterChain#doFilter(ServletRequest, ServletResponse, FilterChain)
 * doFilter} method execution), which means that it is NOT possible to
 * use various transaction propagation modes in your service methods.
 * You would normally use declarative transaction model supported by
 * Spring or EJB container in serious applications.
 */
public class OpenEntityManagerInViewFilter implements Filter {
    
    private static final String SEPARATOR = ",";
	private static final String FILTER_PARAM_PERSISTENCE_UNIT_NAMES = "persistenceUnitNames";
    private static final String DEFAULT_PERSISTENCE_UNIT_NAME = "persistence-unit";
    
    private static final Logger log = LoggerFactory.getLogger(OpenEntityManagerInViewFilter.class);
    
    private EntityManagerFactory[] entityManagerFactories;
    
    private String[] persistenceUnitNames;
    
    /**
     * Initializes filter while creating instances of {@link EntityManagerFactory}
     * for specified persistence unit names.
     * <p>
     * You can set persistence unit names by providing <tt>persistenceUnitNames</tt>
     * init parameter which should contain a comma-separated list of persistence unit
     * names (if this list is not provided, only a single persistence unit with default name
     * <tt>persistence-unit</tt> is created).
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    	String parameter = filterConfig.getInitParameter(FILTER_PARAM_PERSISTENCE_UNIT_NAMES);
    	if(parameter == null) {
    		this.persistenceUnitNames = new String[] {DEFAULT_PERSISTENCE_UNIT_NAME};
    	} else {
    		this.persistenceUnitNames = parameter.split(SEPARATOR);
    	}
    	
    	this.entityManagerFactories = new EntityManagerFactory[this.persistenceUnitNames.length];
    	for(int i = 0; i< this.persistenceUnitNames.length; i++) {
    		String persistenceUnitName = this.persistenceUnitNames[i];
    		log.debug("Creating EntityManagerFactory for persistence unit [" + persistenceUnitName + "]");
			this.entityManagerFactories[i] = Persistence.createEntityManagerFactory(persistenceUnitName);
			log.debug("EntityManagerFactory for [" + persistenceUnitName + "] has been created");
    	}
    }
    
    /**
     * Based on request parameter retrieve appropriate {@link EntityManagerFactory} 
     * instance and creates an instance of {@link EntityManager}. This is set in to
     * the {@link EntityManagerHolder} and begins a transaction.
     * <p>
     * After the filter chain has completed, transaction is committed,
     * the {@link EntityManager} is closed and its reference held by
     * {@link EntityManagerHolder} is cleared.
     * <p>
     * Should an exception occur during filter chain execution, the
     * transaction is rolled back and the exception is propagated up
     * to the servlet container.
     */
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        EntityManager em = null;
        EntityTransaction tx = null;
        
        try {
        	EntityManagerFactory entityManagerFactory = getEntityManagerFactory(request);
            em = entityManagerFactory.createEntityManager();
            
            if (! (em instanceof HibernateEntityManager))
                throw new ServletException("EntityManagerFactory did not provide a HibernateEntityManager instance");
            
            EntityManagerHolder.set((HibernateEntityManager) em);
            
            tx = em.getTransaction();
            
            tx.begin();
            doFilterInternal(request, response, chain);
            tx.commit();
        } catch (Exception ex) {
            try {
                tx.rollback();
            } catch (Exception rollbackEx) {
                // swallow rollback errors
                log.error("Error upon transaction rollback", rollbackEx);
            }
            throw new ServletException(ex);
        } finally {
            EntityManagerHolder.set(null);
            
            if (em != null)
                em.close();
        }
    }
    
    protected EntityManagerFactory getEntityManagerFactory(ServletRequest request) {
		return this.entityManagerFactories[0];
	}

	/**
     * Default implementation invokes the
     * {@link FilterChain#doFilter(ServletRequest, ServletResponse, FilterChain)
     * doFilter} method.
     */
    protected void doFilterInternal(ServletRequest request,
            ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        chain.doFilter(request, response);
    }
    
    /**
     * Clean up logic.
     */
    public void destroy() {
    	for(int i = 0; i < this.entityManagerFactories.length; i++) {
    		String persistenceUnitName = this.persistenceUnitNames[i];
    		log.debug("Closing EntityManagerFactory for [" + persistenceUnitName + "]");
    		this.entityManagerFactories[i].close();
    		log.debug("EntityManagerFactory for [" + persistenceUnitName + "] closed");
    	}
    }
    
}
