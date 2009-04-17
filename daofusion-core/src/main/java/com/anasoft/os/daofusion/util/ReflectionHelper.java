package com.anasoft.os.daofusion.util;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for common reflection operations.
 * 
 * @author vojtech.szocs
 */
public final class ReflectionHelper {

	private static final Logger LOG = LoggerFactory.getLogger(ReflectionHelper.class);
	
    private ReflectionHelper() {}
    
    /**
     * Tries to invoke a getter method for the given <tt>propertyName</tt>
     * on the provided <tt>object</tt>.
     * 
     * <p>
     * 
     * Note that the getter method should meet the following requirements:
     * 
     * <ul>
     *  <li>method name must follow the standard getter naming convention
     *  <li>method must be declared as <tt>public</tt>
     *  <li>method cannot accept any arguments
     * </ul>
     * 
     * @param object Object on which to invoke the getter method.
     * @param propertyName Name of the property containing the requested value.
     * @return Getter method invocation result.
     */
    public static Object invokeGetterMethod(Object object, String propertyName) {
        final String getterMethodName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        
        try {
            return object.getClass().getMethod(getterMethodName, new Class[]{}).invoke(object, new Object[]{});
        } catch (Exception e) {
        	LOG.error("Error while invoking getter method '" + getterMethodName + "' on object " + object, e);
        	return null;
        }
    }
    
    /**
     * Tries to resolve the given dot-separated <tt>propertyPath</tt>
     * on the provided <tt>object</tt> by traversing its properties.
     * 
     * <p>
     * 
     * This is a convenient way of accessing nested properties of
     * a root object programatically.
     * 
     * <p>
     * 
     * Note that the method is null-friendly - it won't throw
     * a {@link NullPointerException} during the object traversal
     * (a <tt>null</tt> value is returned instead).
     * 
     * @param object Object on which to resolve the property path.
     * @param propertyPath Dot-separated logical path to the target
     * property.
     * @return Property resolution result.
     */
    public static Object resolvePropertyPath(Object object, String propertyPath) {
        final StringTokenizer propertyPathTokenizer = new StringTokenizer(propertyPath, ".");
        Object result = object;
        
        while (propertyPathTokenizer.hasMoreTokens()) {
            final String propertyPathElement = propertyPathTokenizer.nextToken();
            
            result = invokeGetterMethod(result, propertyPathElement);
            
            if (result == null) {
            	if (propertyPathTokenizer.hasMoreTokens()) {
            		LOG.warn("Denying a NullPointerException - getter for propertyPathElement '{}' returned null while resolving propertyPath '{}'", propertyPathElement, propertyPath);
            	}
            	
                break;
            }
        }
        
        return result;
    }
    
}
