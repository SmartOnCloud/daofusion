package com.anasoft.os.daofusion.cto.server.example;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.anasoft.os.daofusion.criteria.PropertyFilterCriterionProvider;

public final class DirectValueCriterionProviders {

    private static abstract class UnaryDirectValueProvider implements PropertyFilterCriterionProvider {
        public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
            return (directValues.length == 1) && (directValues[0] != null);
        }
    }
    
    private static abstract class BinaryDirectValueProvider implements PropertyFilterCriterionProvider {
        public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
            return (directValues.length == 2) && (directValues[0] != null) && (directValues[1] != null);
        }
    }
    
	public static final PropertyFilterCriterionProvider LIKE = new UnaryDirectValueProvider() {
		public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues, Object[] directValues) {
			return Restrictions.like(targetPropertyName, directValues[0]);
		}
	};
	
	public static final PropertyFilterCriterionProvider EQ = new UnaryDirectValueProvider() {
		public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues, Object[] directValues) {
			return Restrictions.eq(targetPropertyName, directValues[0]);
		}
	};
	
	public static final PropertyFilterCriterionProvider BETWEEN = new BinaryDirectValueProvider() {
		public Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues, Object[] directValues) {
			return Restrictions.between(targetPropertyName, directValues[0], directValues[1]);
		}
	};
	
	private DirectValueCriterionProviders() {
	}
	
}
