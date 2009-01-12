package com.anasoft.os.daofusion.cto.server.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anasoft.os.daofusion.cto.server.FilterAndSortMapping;
import com.anasoft.os.daofusion.cto.server.FilterValueObjectProvider;
import com.anasoft.os.daofusion.cto.server.NestedPropertyCriteriaBasedConverter;

public class SampleConverter extends NestedPropertyCriteriaBasedConverter {

	private static final Logger LOG = LoggerFactory.getLogger(SampleConverter.class);
	
	public static final String MAPPING_GROUP_CUSTOMER = "customer";
	
	public static final String CUSTOMER_NAME_ID = "name";
	public static final String CUSTOMER_NAME_PATH = "name";
	
	public static final String CUSTOMER_FAVNO_ID = "favNo";
	public static final String CUSTOMER_FAVNO_PATH = "userProfile.favoriteNumber";
	
	public static final String CUSTOMER_JOINDATE_ID = "joinDate";
	public static final String CUSTOMER_JOINDATE_PATH = "accountCreated";
	
	public static final String DATE_FORMAT = "yyyy.MM.dd HH:mm";
	
	public void initMappings() {
		addStringMapping(MAPPING_GROUP_CUSTOMER, CUSTOMER_NAME_ID, CUSTOMER_NAME_PATH);
		addIntegerMapping(MAPPING_GROUP_CUSTOMER, CUSTOMER_FAVNO_ID, CUSTOMER_FAVNO_PATH);
		addDateMapping(MAPPING_GROUP_CUSTOMER, CUSTOMER_JOINDATE_ID, CUSTOMER_JOINDATE_PATH);
	}
	
	public Date parseDate(String dateString) {
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(dateString);
		} catch (ParseException e) {
			LOG.error("Error while converting '" + dateString + "' into Date using format " + DATE_FORMAT, e);
			return null;
		}
	}
	
	private void addStringMapping(String mappingGroupName, String propertyId, String propertyPath) {
		addMapping(mappingGroupName, new FilterAndSortMapping(
				propertyId,
				propertyPath,
				DirectValueCriterionProviders.LIKE,
				new FilterValueObjectProvider() {
					public Object getObject(String stringValue) {
						return stringValue;
					}
				}));
	}
	
	private void addIntegerMapping(String mappingGroupName, String propertyId, String propertyPath) {
		addMapping(mappingGroupName, new FilterAndSortMapping(
				propertyId,
				propertyPath,
				DirectValueCriterionProviders.EQ,
				new FilterValueObjectProvider() {
					public Object getObject(String stringValue) {
						return Integer.valueOf(stringValue);
					}
				}));
	}
	
	private void addDateMapping(String mappingGroupName, String propertyId, String propertyPath) {
		addMapping(mappingGroupName, new FilterAndSortMapping(
				propertyId,
				propertyPath,
				DirectValueCriterionProviders.BETWEEN,
				new FilterValueObjectProvider() {
					public Object getObject(String stringValue) {
						return parseDate(stringValue);
					}
				}));
	}
	
}
