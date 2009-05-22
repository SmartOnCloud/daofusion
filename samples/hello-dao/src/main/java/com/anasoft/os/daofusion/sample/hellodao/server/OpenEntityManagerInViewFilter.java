package com.anasoft.os.daofusion.sample.hellodao.server;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
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
 * Simple implementation of the "Open EntityManager In View" pattern.
 * <p>
 * We recommend using Spring's <tt>OpenEntityManagerInViewFilter</tt>
 * in serious applications.
 */
public class OpenEntityManagerInViewFilter implements Filter {
    
    private static final String FILTER_PARAM_PERSISTENCE_UNIT_NAME = "persistenceUnitName";
    private static final String DEFAULT_PERSISTENCE_UNIT_NAME = "persistence-unit";
    
    private static final Logger log = LoggerFactory.getLogger(OpenEntityManagerInViewFilter.class);
    
    private EntityManagerFactory entityManagerFactory;
    
    private String persistenceUnitName;
    
    /**
     * Initializes filter while creating {@link EntityManagerFactory}
     * for specified persistence unit name.
     * <p>
     * You can set persistence unit name by providing <tt>persistenceUnitName</tt>
     * init parameter (if not provided, default persistence unit name
     * <tt>persistence-unit</tt> is used).
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        this.persistenceUnitName = filterConfig.getInitParameter(FILTER_PARAM_PERSISTENCE_UNIT_NAME);
        
        if (this.persistenceUnitName == null)
            this.persistenceUnitName = DEFAULT_PERSISTENCE_UNIT_NAME;
        
        log.debug("Creating EntityManagerFactory for persistence unit [" + persistenceUnitName + "]");
        
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        log.debug("EntityManagerFactory for [" + persistenceUnitName + "] has been created");
    }
    
    /**
     * Creates new instance of {@link EntityManager}, sets it to the
     * {@link EntityManagerHolder} and begins a transaction.
     * <p>
     * After the filter chain has completed, transaction is committed,
     * the {@link EntityManager} is closed and its reference held by
     * {@link EntityManagerHolder} is cleared.
     * <p>
     * Should a {@link PersistenceException} occur during filter chain
     * execution, the transaction is rolled back and the exception is
     * propagated up to the servlet container.
     */
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        EntityManager em = null;
        EntityTransaction tx = null;
        
        try {
            em = entityManagerFactory.createEntityManager();
            
            if (! (em instanceof HibernateEntityManager))
                throw new ServletException("EntityManagerFactory did not provide a HibernateEntityManager instance");
            
            EntityManagerHolder.set((HibernateEntityManager) em);
            
            tx = em.getTransaction();
            
            tx.begin();
            doFilterInternal(request, response, chain);
            tx.commit();
        } catch (PersistenceException pe) {
            try {
                tx.rollback();
            } catch (Exception e) {
                // swallow rollback errors
                log.error("Error upon transaction rollback", e);
            }
            throw pe;
        } finally {
            EntityManagerHolder.set(null);
            
            if (em != null)
                em.close();
        }
    }
    
    /**
     * Default implementation invokes the {@link Filter#doFilter(ServletRequest,
     * ServletResponse, FilterChain) doFilter} method.
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
        log.debug("Closing EntityManagerFactory for [" + persistenceUnitName + "]");
        this.entityManagerFactory.close();
        log.debug("EntityManagerFactory for [" + persistenceUnitName + "] closed");
    }
    
}
