package com.anasoft.os.daofusion.test.h2;

import org.springframework.test.context.ContextConfiguration;

import com.anasoft.os.daofusion.test.AbstractHibernateEnumerationDaoTest;
import com.anasoft.os.daofusion.test.DatabaseContextLocations;

/**
 * Extension of {@link AbstractHibernateEnumerationDaoTest}
 * for an in-memory H2 database instance.
 * 
 * @see AbstractHibernateEnumerationDaoTest
 * 
 * @author vojtech.szocs
 */
@ContextConfiguration(locations = { DatabaseContextLocations.H2 })
public class H2HibernateEnumerationDaoTest extends AbstractHibernateEnumerationDaoTest {

}
