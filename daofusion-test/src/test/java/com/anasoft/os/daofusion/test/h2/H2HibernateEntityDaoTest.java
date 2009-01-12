package com.anasoft.os.daofusion.test.h2;

import org.springframework.test.context.ContextConfiguration;

import com.anasoft.os.daofusion.test.AbstractHibernateEntityDaoTest;
import com.anasoft.os.daofusion.test.DatabaseContextLocations;

/**
 * Extension of {@link AbstractHibernateEntityDaoTest}
 * for an in-memory H2 database instance.
 * 
 * @see AbstractHibernateEntityDaoTest
 * 
 * @author vojtech.szocs
 */
@ContextConfiguration(locations = { DatabaseContextLocations.H2 })
public class H2HibernateEntityDaoTest extends AbstractHibernateEntityDaoTest {

}
