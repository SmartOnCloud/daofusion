package com.anasoft.os.daofusion.sample.hellodao.server.cto;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.anasoft.os.daofusion.criteria.PropertyFilterCriterionProvider;

/**
 * Utility class providing common {@link PropertyFilterCriterionProvider}
 * implementations.
 */
public final class PropertyFilterCriterionProviders {
    
    private PropertyFilterCriterionProviders() {
    }
    
    private static abstract class UnaryDirectValueProvider implements
            PropertyFilterCriterionProvider {
        public boolean enabled(Object[] filterObjectValues,
                Object[] directValues) {
            return (directValues.length == 1) && (directValues[0] != null);
        }
    }
    
    public static final PropertyFilterCriterionProvider LIKE = new UnaryDirectValueProvider() {
        public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues, Object[] directValues) {
            return Restrictions.like(targetPropertyName, directValues[0]);
        }
    };
    
    public static final PropertyFilterCriterionProvider COLLECTION_SIZE_EQ = new UnaryDirectValueProvider() {
        public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues, Object[] directValues) {
            return Restrictions.sizeEq(targetPropertyName, (Integer) directValues[0]);
        }
    };
    
}
