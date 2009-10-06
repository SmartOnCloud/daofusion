package com.anasoft.os.sample.dmf.service.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.springframework.validation.Errors;

public abstract class AbstractFieldValidator<A extends Annotation> implements FieldValidator<A> {

    private static final String ERROR_CODE_SEPARATOR = ".";
    
    private Class<?> targetClass;
    private String fieldName;
    private Object fieldValue;
    private A annotation;
    
    public void configure(Object target, Field field, A annotation) throws IllegalAccessException {
        this.targetClass = target.getClass();
        this.fieldName = field.getName();
        this.fieldValue = field.get(target);
        this.annotation = annotation;
    }
    
    @SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
        return true;
    }
    
    protected A getAnnotation() {
        return annotation;
    }
    
    protected Object getFieldValue() {
        return fieldValue;
    }
    
    protected String getErrorCode(String suffix) {
        StringBuilder code = new StringBuilder(targetClass.getSimpleName())
            .append(ERROR_CODE_SEPARATOR).append(fieldName);
        
        if (suffix != null) {
            code.append(ERROR_CODE_SEPARATOR).append(suffix);
        }
        
        return code.toString();
    }
    
    protected void rejectValue(Errors errors, String errorCode, String defaultMessage) {
        errors.rejectValue(fieldName, errorCode, defaultMessage);
    }
    
    protected void rejectValue(Errors errors, String errorCode, Object[] errorArgs, String defaultMessage) {
        errors.rejectValue(fieldName, errorCode, errorArgs, defaultMessage);
    }
    
    protected boolean rejectedNullValue(Errors errors) {
        if (fieldValue == null) {
            errors.rejectValue(fieldName, getErrorCode("null"), "Null value");
            return true;
        }
        
        return false;
    }
    
}
