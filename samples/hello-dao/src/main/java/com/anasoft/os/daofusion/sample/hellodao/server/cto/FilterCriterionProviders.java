package com.anasoft.os.daofusion.sample.hellodao.server.cto;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.anasoft.os.daofusion.criteria.FilterCriterionProvider;
import com.anasoft.os.daofusion.criteria.SimpleFilterCriterionProvider;
import com.anasoft.os.daofusion.criteria.SimpleFilterCriterionProvider.FilterDataStrategy;

/**
 * Utility class providing common {@link FilterCriterionProvider}
 * implementations.
 */
public final class FilterCriterionProviders {
    
    private FilterCriterionProviders() {
    }
    
    public static final FilterCriterionProvider LIKE = new SimpleFilterCriterionProvider(FilterDataStrategy.DIRECT, 1) {
        public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues, Object[] directValues) {
            return Restrictions.like(targetPropertyName, "%" + (String) directValues[0] + "%");
        }
    };
    
    public static final FilterCriterionProvider COLLECTION_SIZE_EQ = new SimpleFilterCriterionProvider(FilterDataStrategy.DIRECT, 1) {
        public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues, Object[] directValues) {
            return Restrictions.sizeEq(targetPropertyName, (Integer) directValues[0]);
        }
    };
    
}
