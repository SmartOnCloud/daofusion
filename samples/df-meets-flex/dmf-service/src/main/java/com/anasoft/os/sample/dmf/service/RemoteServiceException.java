package com.anasoft.os.sample.dmf.service;

import flex.messaging.MessageException;

public class RemoteServiceException extends MessageException {

    private static final String DEFAULT_CODE = "Server.Processing";
    
    public RemoteServiceException(String message) {
        this(message, DEFAULT_CODE);
    }
    
    public RemoteServiceException(String message, String code) {
        super(message);
        setCode(code);
    }
    
}
