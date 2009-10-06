package com.anasoft.os.sample.dmf.service.validation.impl;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.anasoft.os.sample.dmf.service.validation.AbstractFieldValidator;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LengthValidator extends AbstractFieldValidator<Length> {

    public Class<Length> getAnnotationType() {
        return Length.class;
    }
    
    public void validate(Object target, Errors errors) {
        if (rejectedNullValue(errors))
            return;
        
        String value = (String) getFieldValue();
        int length = value.length();
        int min = getAnnotation().min();
        int max = getAnnotation().max();
        
        boolean minCondition = getAnnotation().minInclusive() ? min <= length : min < length;
        boolean maxCondition = getAnnotation().maxInclusive() ? length <= max : length < max;
        boolean valid = minCondition && maxCondition;
        
        if (!valid)
            rejectValue(errors, getErrorCode("length"), new Object[] {min, max},
                    "Required length between {0} and {1} characters");
    }
    
}
