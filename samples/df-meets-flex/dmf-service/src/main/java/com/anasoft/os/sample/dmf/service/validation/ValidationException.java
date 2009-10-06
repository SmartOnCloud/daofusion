package com.anasoft.os.sample.dmf.service.validation;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.Errors;

import com.anasoft.os.sample.dmf.service.RemoteServiceException;

public class ValidationException extends RemoteServiceException {

    private static final String CODE = "Server.Validation";
    
    public ValidationException(Errors errors, MessageSource messageSource, Locale locale) {
        super(null, CODE);
        setMessage(createErrorMessage(errors, messageSource, locale));
    }
    
    @SuppressWarnings("unchecked")
    private String createErrorMessage(Errors errors, MessageSource messageSource, Locale locale) {
        StringBuilder msg = new StringBuilder("Validation errors (")
            .append(errors.getErrorCount()).append("):\n");
        
        List<MessageSourceResolvable> errorList = errors.getAllErrors();
        for (MessageSourceResolvable error : errorList) {
            msg.append(messageSource.getMessage(error, locale)).append("\n");
        }
        
        return msg.toString().trim();
    }
    
}
