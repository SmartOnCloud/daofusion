package com.anasoft.os.sample.dmf.service.aspect;

public aspect LayerDefinitionAspect {

    public static pointcut inServiceLayer():
        within(com.anasoft.os.sample.dmf.service..*);
    
}
