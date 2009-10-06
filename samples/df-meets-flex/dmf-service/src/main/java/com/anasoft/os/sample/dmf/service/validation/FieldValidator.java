package com.anasoft.os.sample.dmf.service.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.springframework.validation.Validator;

public interface FieldValidator<A extends Annotation> extends Validator {

    void configure(Object target, Field field, A annotation) throws IllegalAccessException;
    
    Class<A> getAnnotationType();
    
}
