public abstract class UnaryDirectValueProvider implements PropertyFilterCriterionProvider {

    public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
        return (directValues.length == 1) && (directValues[0] != null);
    }

}

public abstract class UnaryFilterObjectValueProvider implements PropertyFilterCriterionProvider {

    public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
        return (filterObjectValues.length == 1) && (filterObjectValues[0] != null);
    }

}

public class QueryDefinition<T extends Persistable<?>> {

    private Integer firstResult;
    private Integer maxResults;

    private T filterObject;
    private boolean filterEnabled;

    private boolean sortEnabled;
    private String sortPropertyPath;
    private boolean sortAscending;

    // getters and setters go here

}

public interface OrderDao extends PersistentEntityDao<Order, Long> {

    public List<Order> getOrders(final QueryDefinition<Order> query, Integer minOrderItemCount,
            final String customerNameFilter);

}

public class OrderDaoImpl extends EntityManagerAwareEntityDao<Order, Long> implements OrderDao {

    public Class<Order> getEntityClass() {
        return Order.class;
    }

    public List<Order> getOrders(final QueryDefinition<Order> query, Integer minOrderItemCount,
            final String customerNameFilter) {

        final NestedPropertyCriteria criteria = new NestedPropertyCriteria();

        criteria.setFirstResult(query.getFirstResult());
        criteria.setMaxResults(query.getMaxResults());

        criteria.setFilterObject(query.getFilterObject());

        // direct property filter criterion (using the filterObject)
        criteria.add(new FilterCriterion("creationDate", "creationDate", true,
                new UnaryFilterObjectValueProvider() {

            public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues,
                    Object[] directValues) {

                return Restrictions.eq(targetPropertyName, filterObjectValues[0]);
            }

            @Override
            public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
                return super.enabled(filterObjectValues, directValues) && query.isFilterEnabled();
            }

        }));

        // nested property filter criterion (using the filterObject)
        criteria.add(new FilterCriterion("customer.email", "customer.email", true,
                new UnaryFilterObjectValueProvider() {

            public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues,
                    Object[] directValues) {

                return Restrictions.like(targetPropertyName, filterObjectValues[0]);
            }

            @Override
            public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
                return super.enabled(filterObjectValues, directValues) && query.isFilterEnabled();
            }

        }));

        // direct property filter criterion (using a direct value)
        criteria.add(new FilterCriterion("orderItems", minOrderItemCount, false,
                new UnaryDirectValueProvider() {

            public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues,
                    Object[] directValues) {

                return Restrictions.sizeGe(targetPropertyName, (Integer) directValues[0]);
            }

            @Override
            public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
                return super.enabled(filterObjectValues, directValues) && query.isFilterEnabled();
            }

        }));

        // nested property filter criterion (bypassing the direct value
        // mechanism via the final keyword)
        criteria.add(new FilterCriterion("customer.name", null, false,
                new PropertyFilterCriterionProvider() {

            public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues,
                    Object[] directValues) {

                return Restrictions.like(targetPropertyName, customerNameFilter);
            }

            public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
                return (customerNameFilter != null) && query.isFilterEnabled();
            }

        }));

        if (query.isSortEnabled()) {
            criteria.add(new SortCriterion(query.getSortPropertyPath(), query.isSortAscending()));
        }

        return query(criteria);
    }

}
