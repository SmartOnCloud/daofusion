public abstract class BaseHibernateDataAccessor {

    /**
     * Returns an open HibernateEntityManager instance providing access to the
     * Hibernate Session.
     */
    protected abstract HibernateEntityManager getHibernateEntityManager();

}
