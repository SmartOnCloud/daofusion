package com.anasoft.os.daofusion.test;

/**
 * Class holding Spring test context locations
 * regarding all supported database instances.
 * 
 * @author vojtech.szocs
 */
public final class DatabaseContextLocations {

	// in-memory databases
	public static final String H2 = "classpath:h2/testContext-h2.xml";
	public static final String HSQL = "classpath:hsql/testContext-hsql.xml";
	
	// mid-scale databases
	public static final String MYSQL = "classpath:mysql/testContext-mysql.xml";
	public static final String POSTGRESQL = "classpath:postgresql/testContext-postgresql.xml";
	
	// enterprise databases
	public static final String DB2 = "classpath:db2/testContext-db2.xml";
	public static final String MSSQL = "classpath:mssql/testContext-mssql.xml";
	public static final String ORACLE = "classpath:oracle/testContext-oracle.xml";
	
	private DatabaseContextLocations() {
	}
	
}
