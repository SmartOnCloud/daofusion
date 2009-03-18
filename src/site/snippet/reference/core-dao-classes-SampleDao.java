@Component
public class EntityManagerHolder {

    @PersistenceContext
    private HibernateEntityManager entityManager;

    public HibernateEntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(HibernateEntityManager entityManager) {
        this.entityManager = entityManager;
    }

}

public abstract class EntityManagerAwareEntityDao<T extends Persistable<ID>, ID extends Serializable>
        extends AbstractHibernateEntityDao<T, ID> {

    @Autowired
    private EntityManagerHolder entityManagerHolder;

    @Override
    protected HibernateEntityManager getHibernateEntityManager() {
        return entityManagerHolder.getEntityManager();
    }

}

public interface OrderDao extends PersistentEntityDao<Order, Long> {

    // add some business-related methods here

}

@Component
public class OrderDaoImpl extends EntityManagerAwareEntityDao<Order, Long> implements OrderDao {

    public Class<Order> getEntityClass() {
        return Order.class;
    }

    // add business-related method implementations here

}
