package com.anasoft.os.daofusion.cto.server;

import com.anasoft.os.daofusion.util.SimpleMapContainer;

/**
 * Persistent entity criteria transfer object mapping
 * group representing a set of property mappings for
 * a specific persistent entity in a specific usage
 * scenario.
 * 
 * @see NestedPropertyMapping
 * @see SimpleMapContainer
 * 
 * @author vojtech.szocs
 */
public class NestedPropertyMappingGroup extends SimpleMapContainer<String, NestedPropertyMapping> {

	private final String name;
	
	/**
	 * Creates a new property mapping group.
	 * 
	 * @param name Name of the property mapping group.
	 */
	public NestedPropertyMappingGroup(String name) {
		this.name = name;
	}
	
	/**
	 * @return Name of the property mapping group.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @see com.anasoft.os.daofusion.util.SimpleMapContainer#getKey(java.lang.Object)
	 */
	@Override
	protected String getKey(NestedPropertyMapping object) {
		return object.getPropertyId();
	}
	
}
