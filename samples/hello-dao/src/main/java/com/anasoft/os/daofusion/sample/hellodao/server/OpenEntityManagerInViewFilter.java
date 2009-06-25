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
 * pattern that supports multiple persistence units.
 * <p>
 * We recommend using Spring's <tt>OpenEntityManagerInViewFilter</tt>
 * in serious applications, assuming they don't need to switch between
 * multiple persistence units (entity manager factories) dynamically.
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
     * Initializes the filter by creating {@link EntityManagerFactory}
     * instances for required persistence units.
     * <p>
     * You can specify persistence unit names by providing <tt>persistenceUnitNames</tt>
     * init parameter containing a comma-separated list of persistence unit names
     * (if not specified, a single persistence unit with the default name
     * <tt>persistence-unit</tt> is created).
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    	String unitNames = filterConfig.getInitParameter(FILTER_PARAM_PERSISTENCE_UNIT_NAMES);
    	
    	if(unitNames == null) {
    		persistenceUnitNames = new String[] {DEFAULT_PERSISTENCE_UNIT_NAME};
    	} else {
    		persistenceUnitNames = unitNames.split(SEPARATOR);
    	}
    	
    	entityManagerFactories = new EntityManagerFactory[persistenceUnitNames.length];
    	
    	for (int i = 0; i< persistenceUnitNames.length; i++) {
    		String persistenceUnitName = persistenceUnitNames[i];
    		
    		log.debug("Creating EntityManagerFactory for persistence unit [" + persistenceUnitName + "]");
			entityManagerFactories[i] = Persistence.createEntityManagerFactory(persistenceUnitName);
			log.debug("EntityManagerFactory for [" + persistenceUnitName + "] has been created");
    	}
    }
    
    /**
     * Retrieves appropriate {@link EntityManagerFactory} (based on the request)
     * and creates an {@link EntityManager} to be used by the current thread.
     * The {@link EntityManager} is set into {@link EntityManagerHolder} and
     * a transaction is started.
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
    
    /**
     * Selects an appropriate {@link EntityManagerFactory} based on the request.
     * <p>
     * Default implementation always selects the first entity manager factory
     * corresponding to the first persistence unit declared in filter configuration.
     * <p>
     * Override this method if you need to switch between multiple persistence units
     * dynamically.
     */
    protected EntityManagerFactory getEntityManagerFactory(ServletRequest request) {
		return entityManagerFactories[0];
	}
    
	/**
     * Default implementation invokes the
     * {@link FilterChain#doFilter(ServletRequest, ServletResponse, FilterChain)
     * doFilter} method.
     * <p>
     * Override this method if you need to extend this default behavior.
     */
    protected void doFilterInternal(ServletRequest request,
            ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        chain.doFilter(request, response);
    }
    
    /**
     * Destroys the filter by closing all {@link EntityManagerFactory}
     * instances managed by this filter.
     */
    public void destroy() {
    	for (int i = 0; i < entityManagerFactories.length; i++) {
    		String persistenceUnitName = persistenceUnitNames[i];
    		
    		log.debug("Closing EntityManagerFactory for [" + persistenceUnitName + "]");
    		entityManagerFactories[i].close();
    		log.debug("EntityManagerFactory for [" + persistenceUnitName + "] closed");
    	}
    }
    
}
