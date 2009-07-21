package com.anasoft.os.daofusion.cto.server.example;

import java.util.Date;

import com.anasoft.os.daofusion.criteria.AssociationPath;
import com.anasoft.os.daofusion.criteria.AssociationPathElement;
import com.anasoft.os.daofusion.cto.server.FilterAndSortMapping;
import com.anasoft.os.daofusion.cto.server.NestedPropertyCriteriaBasedConverter;
import com.anasoft.os.daofusion.util.FilterValueConverters;
import com.anasoft.os.daofusion.util.FilterValueConverters.DateConverter;

public class SampleConverter extends NestedPropertyCriteriaBasedConverter {

	public static final String MAPPING_GROUP_CUSTOMER = "customer";
	
	public static final String CUSTOMER_NAME_ID = "name";
	public static final AssociationPath CUSTOMER_NAME_APATH = AssociationPath.ROOT;
	public static final String CUSTOMER_NAME_TARGET = "name";
	
	public static final String CUSTOMER_FAVNO_ID = "favNo";
	public static final AssociationPath CUSTOMER_FAVNO_APATH = new AssociationPath(new AssociationPathElement("userProfile"));
	public static final String CUSTOMER_FAVNO_TARGET = "favoriteNumber";
	
	public static final String CUSTOMER_JOINDATE_ID = "joinDate";
	public static final AssociationPath CUSTOMER_JOINDATE_APATH = AssociationPath.ROOT;
	public static final String CUSTOMER_JOINDATE_TARGET = "accountCreated";
	
	public static final DateConverter DATE_CONVERTER = new DateConverter("yyyy.MM.dd HH:mm");
	
	public Date parseDate(String value) {
	    return DATE_CONVERTER.parseDate(value, DATE_CONVERTER.getDateFormatPattern());
	}
	
	public void initMappings() {
		addStringMapping(MAPPING_GROUP_CUSTOMER, CUSTOMER_NAME_ID, CUSTOMER_NAME_APATH, CUSTOMER_NAME_TARGET);
		addIntegerMapping(MAPPING_GROUP_CUSTOMER, CUSTOMER_FAVNO_ID, CUSTOMER_FAVNO_APATH, CUSTOMER_FAVNO_TARGET);
		addDateMapping(MAPPING_GROUP_CUSTOMER, CUSTOMER_JOINDATE_ID, CUSTOMER_JOINDATE_APATH, CUSTOMER_JOINDATE_TARGET);
	}
	
	private void addStringMapping(String mappingGroupName, String propertyId, AssociationPath associationPath, String targetPropertyName) {
		addMapping(mappingGroupName, new FilterAndSortMapping<String>(
				propertyId, associationPath, targetPropertyName,
				CtoFilterCriterionProviders.LIKE, FilterValueConverters.STRING));
	}
	
	private void addIntegerMapping(String mappingGroupName, String propertyId, AssociationPath associationPath, String targetPropertyName) {
		addMapping(mappingGroupName, new FilterAndSortMapping<Integer>(
                propertyId, associationPath, targetPropertyName,
				CtoFilterCriterionProviders.EQ, FilterValueConverters.INTEGER));
	}
	
	private void addDateMapping(String mappingGroupName, String propertyId, AssociationPath associationPath, String targetPropertyName) {
		addMapping(mappingGroupName, new FilterAndSortMapping<Date>(
                propertyId, associationPath, targetPropertyName,
				CtoFilterCriterionProviders.BETWEEN, DATE_CONVERTER));
	}
	
}
