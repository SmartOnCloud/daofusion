package com.anasoft.os.daofusion.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.impl.CriteriaImpl.Subcriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Register of {@link AssociationPath} instances and corresponding
 * Hibernate {@link Criteria} to be used when updating the root
 * {@link Criteria} instance.
 * 
 * <p>
 * 
 * This class is used by {@link NestedPropertyCriteria} to prepare
 * subcriteria mappings in a safe way, avoiding the
 * <a href="http://opensource.atlassian.com/projects/hibernate/browse/HHH-879">
 * duplicate association path</a> Hibernate Criteria API issue.
 * 
 * <p>
 * 
 * You can use this class to modify existing {@link Criteria} instances
 * as well if necessary (always prefer {@link #add(AssociationPath)} in
 * favor of calling the {@link Criteria#createCriteria(String, String, int)
 * createCriteria} method directly).
 * 
 * @see AssociationPath
 * @see NestedPropertyCriteria
 * 
 * @author michal.jemala
 * @author vojtech.szocs
 */
public class AssociationPathRegister {

    private static final Logger LOG = LoggerFactory.getLogger(AssociationPathRegister.class);
    
	private final Map<AssociationPath, Criteria> pathToCriteriaMap = new HashMap<AssociationPath, Criteria>();
	
	/**
	 * Creates a new association path register, rooted
	 * at the given {@link Criteria} instance.
	 * 
	 * <p>
	 * 
	 * Note that the register is initialized with Hibernate
	 * {@link Criteria} mappings according to the current
	 * state of <tt>rootCriteria</tt>.
	 * 
	 * @param rootCriteria Root {@link Criteria} instance.
	 */
	@SuppressWarnings("unchecked")
    public AssociationPathRegister(Criteria rootCriteria) {
	    if (CriteriaImpl.class.isAssignableFrom(rootCriteria.getClass())) {
	        Iterator<Subcriteria> subCriteriaIterator = CriteriaImpl.class.cast(rootCriteria).iterateSubcriteria();
	        
	        while (subCriteriaIterator.hasNext()) {
                Subcriteria subCriteria = subCriteriaIterator.next();
                AssociationPath associationPath = getSubCriteriaAssociationPath(subCriteria);
                
                pathToCriteriaMap.put(associationPath, subCriteria);
            }
	    } else {
	        LOG.warn("rootCriteria is not a Hibernate CriteriaImpl but {}", rootCriteria.getClass().getName());
	    }
	    
		pathToCriteriaMap.put(new AssociationPath(), rootCriteria);
	}
	
	/**
     * Returns an association path for the given <tt>subCriteria</tt>
     * by traversing its {@link Subcriteria} parents.
     * 
     * @param subCriteria {@link Subcriteria} instance to check.
     * @return {@link AssociationPath} for the given <tt>subCriteria</tt>.
     */
    private AssociationPath getSubCriteriaAssociationPath(Subcriteria subCriteria){
        List<AssociationPathElement> elementList = new ArrayList<AssociationPathElement>();
        elementList.add(new AssociationPathElement(subCriteria.getPath()));
        
        Subcriteria currentSubCriteria = subCriteria;
        
        while (currentSubCriteria.getParent() != null
                && Subcriteria.class.isAssignableFrom(currentSubCriteria.getParent().getClass())) {
            currentSubCriteria = Subcriteria.class.cast(currentSubCriteria.getParent());
            elementList.add(0, new AssociationPathElement(currentSubCriteria.getPath()));
        }
        
        return new AssociationPath(elementList.toArray(new AssociationPathElement[0]));
    }
	
	/**
	 * Returns a {@link Criteria} instance for the given
	 * {@link AssociationPath} or <tt>null</tt> in case
	 * there is no such mapping.
	 * 
	 * <p>
	 * 
	 * Use the {@link #add(AssociationPath)} method to
	 * initialize Hibernate {@link Criteria} mappings
	 * prior to calling this method.
	 * 
	 * @param associationPath Association path for which
	 * to obtain the {@link Criteria} instance.
	 * @return {@link Criteria} instance for the given
     * {@link AssociationPath} (can be <tt>null</tt>).
	 */
	public Criteria get(AssociationPath associationPath) {
	    if (!pathToCriteriaMap.containsKey(associationPath)) {
    	    for (AssociationPath partialPath : associationPath) {
                if (!pathToCriteriaMap.containsKey(partialPath)) {
                    AssociationPath superPath = partialPath.getSuperPath();
                    AssociationPathElement lastElement = partialPath.getLastElement();
                    
                    Criteria parentCriteria = pathToCriteriaMap.get(superPath);
                    Criteria criteria = parentCriteria.createCriteria(
                            lastElement.getValue(),
                            lastElement.getJoinType().getHibernateJoinType());
                    
                    pathToCriteriaMap.put(partialPath, criteria);
                }
            }
	    }
	    
		return pathToCriteriaMap.get(associationPath);
	}
	
}
