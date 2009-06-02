package com.anasoft.os.daofusion.cto.server;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anasoft.os.daofusion.criteria.NestedPropertyCriteria;
import com.anasoft.os.daofusion.criteria.PersistentEntityCriteria;
import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;
import com.anasoft.os.daofusion.util.SimpleMapContainer;

/**
 * Generic persistent entity criteria transfer object
 * converter implementation using {@link NestedPropertyCriteria}
 * as the server-side criteria implementation.
 * 
 * <p>
 * 
 * This class uses persistent entity property mappings
 * ({@link NestedPropertyMapping} instances) to define
 * relations between the {@link CriteriaTransferObject}
 * and {@link NestedPropertyCriteria} for a specific
 * persistent entity via {@link NestedPropertyMappingGroup}
 * definitions.
 * 
 * @see CriteriaTransferObjectConverter
 * @see NestedPropertyMapping
 * @see NestedPropertyMappingGroup
 * @see SimpleMapContainer
 * 
 * @author vojtech.szocs
 */
public class NestedPropertyCriteriaBasedConverter extends SimpleMapContainer<String, NestedPropertyMappingGroup> implements CriteriaTransferObjectConverter {

	private static final Logger LOG = LoggerFactory.getLogger(NestedPropertyCriteriaBasedConverter.class);
	
	/**
	 * Adds the given {@link NestedPropertyMapping}
	 * to this converter by associating it with the
	 * requested property mapping group.
	 * 
	 * <p>
	 * 
	 * Note that the method creates the mapping
	 * group if not found within the converter.
	 * 
	 * <p>
	 * 
	 * This is the preferred way of configuring
	 * property mappings for this converter
	 * implementation.
	 * 
	 * @param mappingGroupName Name of the property
	 * mapping group.
	 * @param mapping Persistent entity property
	 * mapping to add.
	 */
	public void addMapping(String mappingGroupName, NestedPropertyMapping mapping) {
		if (!containsKey(mappingGroupName)) {
			LOG.info("Creating new property mapping group '{}'", mappingGroupName);
			add(new NestedPropertyMappingGroup(mappingGroupName));
		}
		
		NestedPropertyMappingGroup group = getObjectMap().get(mappingGroupName);
		group.add(mapping);
	}
	
	/**
	 * Returns a map of symbolic persistent entity property
	 * identifiers (<tt>propertyId</tt> values) with
	 * corresponding {@link NestedPropertyMapping} instances
	 * for the requested property mapping group.
	 * 
	 * @param mappingGroupName Name of the property
	 * mapping group.
	 * @return Property mappings for the requested
	 * property mapping group or <tt>null</tt> in
	 * case there is no such mapping group.
	 */
	private Map<String, NestedPropertyMapping> getPropertyMappings(String mappingGroupName) {
		if (!containsKey(mappingGroupName)) {
			LOG.warn("Requested property mapping group '{}' not found - did you forget to add mappings for this one?", mappingGroupName);
			return null;
		} else {
			return getObjectMap().get(mappingGroupName).getObjectMap();
		}
	}
	
	/**
	 * @see com.anasoft.os.daofusion.cto.server.CriteriaTransferObjectConverter#convert(com.anasoft.os.daofusion.cto.client.CriteriaTransferObject, java.lang.String)
	 */
	public PersistentEntityCriteria convert(CriteriaTransferObject transferObject, String mappingGroupName) {
		NestedPropertyCriteria nestedCriteria = new NestedPropertyCriteria();
		
		Set<String> propertyIdSet = transferObject.getPropertyIdSet();
		Map<String, NestedPropertyMapping> propertyMappings = getPropertyMappings(mappingGroupName);
		
		for (String propertyId : propertyIdSet) {
		    FilterAndSortCriteria clientSideCriteria = transferObject.get(propertyId);
		    NestedPropertyMapping mapping = propertyMappings.get(propertyId);
            
            if (mapping != null) {
                mapping.apply(clientSideCriteria, nestedCriteria);
            } else {
                LOG.warn("Mapping for propertyId '{}' within the mapping group '{}' not found - skipping property conversion", propertyId, mappingGroupName);
            }
		}
		
		nestedCriteria.setFirstResult(transferObject.getFirstResult());
		nestedCriteria.setMaxResults(transferObject.getMaxResults());
		
		return nestedCriteria;
	}
	
	/**
	 * @see com.anasoft.os.daofusion.util.SimpleMapContainer#getKey(java.lang.Object)
	 */
	@Override
	protected String getKey(NestedPropertyMappingGroup object) {
		return object.getName();
	}
	
}
