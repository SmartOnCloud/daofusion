package com.anasoft.os.sample.dmf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.flex.core.ExceptionTranslator;
import org.springframework.security.SpringSecurityException;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import flex.messaging.MessageException;

@Component
public class RuntimeExceptionTranslator implements ExceptionTranslator {

    // TODO use CommonLogger annotation
    private static final Logger LOG = LoggerFactory.getLogger(RuntimeExceptionTranslator.class);
    
    public boolean handles(Class<?> clazz) {
        return ClassUtils.isAssignable(RuntimeException.class, clazz)
            && !ClassUtils.isAssignable(SpringSecurityException.class, clazz);
    }
    
    public MessageException translate(Throwable t) {
        LOG.error("RuntimeException occured while processing client request", t);
        throw new RemoteServiceException("RuntimeException occured on the server, check logs for details.");
    }
    
}
