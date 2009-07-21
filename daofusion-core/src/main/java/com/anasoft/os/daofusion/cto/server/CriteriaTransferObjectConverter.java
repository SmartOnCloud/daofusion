package com.anasoft.os.daofusion.cto.server;

import com.anasoft.os.daofusion.criteria.PersistentEntityCriteria;
import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;

/**
 * Persistent entity criteria transfer object converter
 * contract defining conversion pattern between client-side
 * {@link CriteriaTransferObject} instances and their
 * corresponding server-side {@link PersistentEntityCriteria}
 * counterparts.
 * 
 * @see CriteriaTransferObject
 * @see PersistentEntityCriteria
 * 
 * @author vojtech.szocs
 */
public interface CriteriaTransferObjectConverter {

	/**
	 * Converts the given {@link CriteriaTransferObject} instance
	 * into a corresponding {@link PersistentEntityCriteria}
	 * according to property mappings defined by the requested
	 * property mapping group.
	 * 
	 * @param transferObject {@link CriteriaTransferObject}
	 * instance to convert.
	 * @param mappingGroupName Name of the property mapping
	 * group to use.
	 * @return Resulting {@link PersistentEntityCriteria}
	 * instance.
	 */
	PersistentEntityCriteria convert(CriteriaTransferObject transferObject, String mappingGroupName);
	
}
