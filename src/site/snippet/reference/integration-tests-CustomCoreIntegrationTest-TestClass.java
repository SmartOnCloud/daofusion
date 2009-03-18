@ContextConfiguration(locations = { DatabaseContextLocations.POSTGRESQL })
@IfProfileValue(name = BaseHibernateIntegrationTest.PROFILE_DBTYPE_NAME, values = {
        BaseHibernateIntegrationTest.PROFILE_DBTYPE_VALUE_ALL,
        BaseHibernateIntegrationTest.PROFILE_DBTYPE_VALUE_LOCAL })
public class PostgreSQLHibernateEntityDaoTest extends AbstractHibernateEntityDaoTest {

}
