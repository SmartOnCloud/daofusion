package com.anasoft.os.daofusion.test.hsql;

import org.springframework.test.context.ContextConfiguration;

import com.anasoft.os.daofusion.test.AbstractHibernateEntityDaoTest;
import com.anasoft.os.daofusion.test.DatabaseContextLocations;

/**
 * Extension of {@link AbstractHibernateEntityDaoTest}
 * for an in-memory HSQL database instance.
 * 
 * @see AbstractHibernateEntityDaoTest
 * 
 * @author vojtech.szocs
 */
@ContextConfiguration(locations = { DatabaseContextLocations.HSQL })
public class HSQLHibernateEntityDaoTest extends AbstractHibernateEntityDaoTest {

}
