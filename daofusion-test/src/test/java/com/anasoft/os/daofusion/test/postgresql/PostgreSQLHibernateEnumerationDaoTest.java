package com.anasoft.os.daofusion.test.postgresql;

import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;

import com.anasoft.os.daofusion.test.AbstractHibernateEnumerationDaoTest;
import com.anasoft.os.daofusion.test.BaseHibernateCoreIntegrationTest;
import com.anasoft.os.daofusion.test.DatabaseContextLocations;

/**
 * Extension of {@link AbstractHibernateEnumerationDaoTest}
 * for a local PostgreSQL database instance.
 * 
 * @see AbstractHibernateEnumerationDaoTest
 * 
 * @author vojtech.szocs
 */
@ContextConfiguration(locations = { DatabaseContextLocations.POSTGRESQL })
@IfProfileValue(name = BaseHibernateCoreIntegrationTest.PROFILE_DBTYPE_NAME, values = { BaseHibernateCoreIntegrationTest.PROFILE_DBTYPE_VALUE_ALL, BaseHibernateCoreIntegrationTest.PROFILE_DBTYPE_VALUE_LOCAL })
public class PostgreSQLHibernateEnumerationDaoTest extends AbstractHibernateEnumerationDaoTest {

}
