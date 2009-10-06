package com.anasoft.os.sample.dmf.service.cto;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.anasoft.os.daofusion.criteria.FilterCriterionProvider;
import com.anasoft.os.daofusion.criteria.SimpleFilterCriterionProvider;
import com.anasoft.os.daofusion.criteria.SimpleFilterCriterionProvider.FilterDataStrategy;

public final class FilterCriterionProviders {

    private FilterCriterionProviders() {}
    
    public static final FilterCriterionProvider LIKE = new SimpleFilterCriterionProvider(FilterDataStrategy.DIRECT, 1) {
        public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues, Object[] directValues) {
            return Restrictions.like(targetPropertyName, "%" + (String) directValues[0] + "%");
        }
    };
    
    public static final FilterCriterionProvider EQ = new SimpleFilterCriterionProvider(FilterDataStrategy.DIRECT, 1) {
        public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues, Object[] directValues) {
            return Restrictions.eq(targetPropertyName, directValues[0]);
        }
    };
    
}
