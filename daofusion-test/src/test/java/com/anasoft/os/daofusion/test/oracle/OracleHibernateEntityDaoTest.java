package com.anasoft.os.daofusion.test.oracle;

import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;

import com.anasoft.os.daofusion.test.AbstractHibernateEntityDaoTest;
import com.anasoft.os.daofusion.test.BaseHibernateIntegrationTest;
import com.anasoft.os.daofusion.test.DatabaseContextLocations;

/**
 * Extension of {@link AbstractHibernateEntityDaoTest}
 * for a remote Oracle 10g database instance.
 * 
 * @see AbstractHibernateEntityDaoTest
 * 
 * @author vojtech.szocs
 */
@ContextConfiguration(locations = { DatabaseContextLocations.ORACLE })
@IfProfileValue(name = BaseHibernateIntegrationTest.PROFILE_DBTYPE_NAME, values = { BaseHibernateIntegrationTest.PROFILE_DBTYPE_VALUE_ALL, BaseHibernateIntegrationTest.PROFILE_DBTYPE_VALUE_REMOTE })
public class OracleHibernateEntityDaoTest extends AbstractHibernateEntityDaoTest {

}
