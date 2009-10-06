package com.anasoft.os.sample.dmf.service.validation.impl;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.anasoft.os.sample.dmf.service.validation.AbstractFieldValidator;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class NotEmptyValidator extends AbstractFieldValidator<NotEmpty> {

    public Class<NotEmpty> getAnnotationType() {
        return NotEmpty.class;
    }
    
    public void validate(Object target, Errors errors) {
        if (rejectedNullValue(errors))
            return;
        
        String value = (String) getFieldValue();
        
        if (!StringUtils.hasText(value))
            rejectValue(errors, getErrorCode("empty"),
                    "Must not be empty");
    }
    
}
