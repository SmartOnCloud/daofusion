package com.anasoft.os.sample.dmf.service;

public interface RemoteService {

    <T extends Result> T process(Command<T> command) throws RemoteServiceException;
    
}
