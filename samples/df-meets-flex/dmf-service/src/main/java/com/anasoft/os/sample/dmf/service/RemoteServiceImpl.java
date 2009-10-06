package com.anasoft.os.sample.dmf.service;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

@Service("remoteService")
@RemotingDestination(channels = {"my-amf"})
public class RemoteServiceImpl implements RemoteService {

    @RemotingInclude
    public <T extends Result> T process(Command<T> command) throws RemoteServiceException {
        return command.execute();
    }
    
}
