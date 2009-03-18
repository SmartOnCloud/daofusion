public final class DirectValueCriterionProviders {

    private static abstract class UnaryDirectValueProvider implements
            PropertyFilterCriterionProvider {

        public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
            return (directValues.length == 1) && (directValues[0] != null);
        }
    }

    private static abstract class BinaryDirectValueProvider implements
            PropertyFilterCriterionProvider {

        public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
            return (directValues.length == 2) && (directValues[0] != null)
                    && (directValues[1] != null);
        }
    }

    public static final PropertyFilterCriterionProvider LIKE = new UnaryDirectValueProvider() {
        public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues,
                Object[] directValues) {

            return Restrictions.like(targetPropertyName, directValues[0]);
        }
    };

    public static final PropertyFilterCriterionProvider EQ = new UnaryDirectValueProvider() {
        public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues,
                Object[] directValues) {

            return Restrictions.eq(targetPropertyName, directValues[0]);
        }
    };

    public static final PropertyFilterCriterionProvider BETWEEN = new BinaryDirectValueProvider() {
        public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues,
                Object[] directValues) {

            return Restrictions.between(targetPropertyName, directValues[0], directValues[1]);
        }
    };

    private DirectValueCriterionProviders() {
    }

}

public class SampleConverter extends NestedPropertyCriteriaBasedConverter {

    public static final String MAPPING_GROUP_CUSTOMER = "customer";

    public static final String CUSTOMER_NAME_ID = "name";
    public static final String CUSTOMER_NAME_PATH = "name";

    public static final String CUSTOMER_FAVNO_ID = "favNo";
    public static final String CUSTOMER_FAVNO_PATH = "userProfile.favoriteNumber";

    public static final String CUSTOMER_JOINDATE_ID = "joinDate";
    public static final String CUSTOMER_JOINDATE_PATH = "accountCreated";

    public static final String DATE_FORMAT = "yyyy.MM.dd HH:mm";

    public SampleConverter() {
        addStringMapping(MAPPING_GROUP_CUSTOMER, CUSTOMER_NAME_ID, CUSTOMER_NAME_PATH);
        addIntegerMapping(MAPPING_GROUP_CUSTOMER, CUSTOMER_FAVNO_ID, CUSTOMER_FAVNO_PATH);
        addDateMapping(MAPPING_GROUP_CUSTOMER, CUSTOMER_JOINDATE_ID, CUSTOMER_JOINDATE_PATH);
    }

    public Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(dateString);
        } catch (ParseException e) {
            // log the exception
            return null;
        }
    }

    private void addStringMapping(String mappingGroupName, String propertyId, String propertyPath) {

        addMapping(
                mappingGroupName,
                new FilterAndSortMapping(propertyId, propertyPath, DirectValueCriterionProviders.LIKE,
                        new FilterValueObjectProvider() {
                            public Object getObject(String stringValue) {
                                return stringValue;
                            }
                        }));
    }

    private void addIntegerMapping(String mappingGroupName, String propertyId, String propertyPath) {

        addMapping(
                mappingGroupName,
                new FilterAndSortMapping(propertyId, propertyPath, DirectValueCriterionProviders.EQ,
                        new FilterValueObjectProvider() {
                            public Object getObject(String stringValue) {
                                return Integer.valueOf(stringValue);
                            }
                        }));
    }

    private void addDateMapping(String mappingGroupName, String propertyId, String propertyPath) {

        addMapping(
                mappingGroupName,
                new FilterAndSortMapping(propertyId, propertyPath, DirectValueCriterionProviders.BETWEEN,
                        new FilterValueObjectProvider() {
                            public Object getObject(String stringValue) {
                                return parseDate(stringValue);
                            }
                        }));
    }

}
