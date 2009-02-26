package com.anasoft.os.daofusion.test.mysql;

import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;

import com.anasoft.os.daofusion.test.AbstractHibernateEnumerationDaoTest;
import com.anasoft.os.daofusion.test.BaseHibernateIntegrationTest;
import com.anasoft.os.daofusion.test.DatabaseContextLocations;

/**
 * Extension of {@link AbstractHibernateEnumerationDaoTest}
 * for a local MySQL database instance.
 * 
 * @see AbstractHibernateEnumerationDaoTest
 * 
 * @author vojtech.szocs
 */
@ContextConfiguration(locations = { DatabaseContextLocations.MYSQL })
@IfProfileValue(name = BaseHibernateIntegrationTest.PROFILE_DBTYPE_NAME, values = { BaseHibernateIntegrationTest.PROFILE_DBTYPE_VALUE_ALL, BaseHibernateIntegrationTest.PROFILE_DBTYPE_VALUE_LOCAL })
public class MySQLHibernateEnumerationDaoTest extends AbstractHibernateEnumerationDaoTest {

}
