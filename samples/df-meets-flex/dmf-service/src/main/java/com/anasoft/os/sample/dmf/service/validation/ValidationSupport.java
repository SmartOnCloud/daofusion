package com.anasoft.os.sample.dmf.service.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public abstract class ValidationSupport {

    protected void validate(final Object target) throws ValidationException {
        final Errors errors = new BeanPropertyBindingResult(target, target.getClass().getSimpleName());
        
        FieldValidator<Annotation>[] validators = getValidators();
        final Map<Class<Annotation>, FieldValidator<Annotation>> validatorMap = new HashMap<Class<Annotation>, FieldValidator<Annotation>>();
        for (FieldValidator<Annotation> validator : validators) {
            validatorMap.put(validator.getAnnotationType(), validator);
        }
        
        FieldCallback validationCallback = new FieldCallback() {
            
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                Annotation[] fieldAnnotations = field.getAnnotations();
                
                for (Annotation annotation : fieldAnnotations) {
                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    
                    if (validatorMap.containsKey(annotationType)) {
                        FieldValidator<Annotation> validator = validatorMap.get(annotationType);
                        
                        if (validator.supports(target.getClass())) {
                            validator.configure(target, field, annotation);
                            validator.validate(target, errors);
                        }
                    }
                }
            }
            
        };
        
        ReflectionUtils.doWithFields(target.getClass(), validationCallback);
        
        if (errors.hasErrors())
            throw new ValidationException(errors, getMessageSource(), getLocale());
    }
    
    protected abstract FieldValidator<Annotation>[] getValidators();
    
    protected abstract MessageSource getMessageSource();
    
    protected abstract Locale getLocale();
    
}
