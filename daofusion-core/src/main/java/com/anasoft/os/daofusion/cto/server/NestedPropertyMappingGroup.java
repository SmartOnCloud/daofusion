package com.anasoft.os.daofusion.cto.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Persistent entity criteria transfer object mapping
 * group representing a set of property mappings for
 * a specific persistent entity in a specific usage
 * scenario.
 * 
 * <p>
 * 
 * Note that each <tt>propertyId</tt> can have multiple
 * mappings associated. This means that there can be
 * multiple {@link NestedPropertyMapping mappings} for
 * handling the same <tt>propertyId</tt> during the
 * criteria transfer object conversion.
 * 
 * @see NestedPropertyMapping
 * 
 * @author vojtech.szocs
 */
public class NestedPropertyMappingGroup {

	private final String name;
	
	private final Map<String, List<NestedPropertyMapping>> propertyMappings = new HashMap<String, List<NestedPropertyMapping>>();
	
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
	 * Adds the given property mapping to this mapping group.
	 * 
	 * @param mapping Property mapping to add.
	 */
	public void add(NestedPropertyMapping mapping) {
	    String propertyId = mapping.getPropertyId();
	    
        if (!propertyMappings.containsKey(propertyId))
	        propertyMappings.put(propertyId, new ArrayList<NestedPropertyMapping>());
	    
	    propertyMappings.get(propertyId).add(mapping);
	}
	
    /**
     * Returns all property mappings contained within this
     * mapping group.
     * 
     * <p>
     * 
     * Property mappings are represented as a map of symbolic
     * persistent entity property identifiers (<tt>propertyId</tt>
     * values) with corresponding {@link NestedPropertyMapping} instances.
     * 
     * <p>
     * 
     * Note that the method returns an <em>unmodifiable</em>
     * map to prevent further map manipulation.
     * 
     * @return Property mappings for this mapping group.
     */
    public Map<String, List<NestedPropertyMapping>> getPropertyMappings() {
        return Collections.unmodifiableMap(propertyMappings);
    }
	
}
