package com.anasoft.os.daofusion.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.anasoft.os.daofusion.cto.server.FilterValueConverter;

/**
 * Utility class containing most common {@link FilterValueConverter}
 * implementations.
 * 
 * @see FilterValueConverter
 * 
 * @author vojtech.szocs
 */
public final class FilterValueConverters {

    private FilterValueConverters() {}
    
    /**
     * {@link Date} converter with configurable date format pattern.
     * 
     * @see SimpleDateFormat
     * 
     * @author vojtech.szocs
     */
    public static class DateConverter implements FilterValueConverter {
        
        private final String dateFormatPattern;
        
        /**
         * Creates a new converter.
         * 
         * @param dateFormatPattern Pattern describing the date format.
         */
        public DateConverter(String dateFormatPattern) {
            this.dateFormatPattern = dateFormatPattern;
        }
        
        /**
         * @return Pattern describing the date format.
         */
        public String getDateFormatPattern() {
            return dateFormatPattern;
        }
        
        /**
         * @see com.anasoft.os.daofusion.cto.server.FilterValueConverter#convert(java.lang.String)
         */
        public Object convert(String stringValue) {
            return parseDate(stringValue, dateFormatPattern);
        }
        
        public Date parseDate(String value, String dateFormatPattern) {
            try {
                return new SimpleDateFormat(dateFormatPattern).parse(value);
            } catch (ParseException e) {
                throw new RuntimeException("Error while converting '" + value + "' into Date using pattern " + dateFormatPattern, e);
            }
        }
        
    }
    
    public static final FilterValueConverter STRING = new FilterValueConverter() {
        public Object convert(String stringValue) {
            return stringValue;
        }
    };
    
    public static final FilterValueConverter INTEGER = new FilterValueConverter() {
        public Object convert(String stringValue) {
            return Integer.valueOf(stringValue);
        }
    };
    
    public static final FilterValueConverter LONG = new FilterValueConverter() {
        public Object convert(String stringValue) {
            return Long.valueOf(stringValue);
        }
    };
    
    public static final FilterValueConverter BOOLEAN = new FilterValueConverter() {
        public Object convert(String stringValue) {
            return Boolean.valueOf(stringValue);
        }
    };
    
}
