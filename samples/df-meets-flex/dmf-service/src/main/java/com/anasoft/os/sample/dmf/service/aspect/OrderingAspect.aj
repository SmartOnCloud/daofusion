package com.anasoft.os.sample.dmf.service.aspect;

public aspect OrderingAspect {

    declare precedence: SecurityAspect, ValidationAspect, *..*TransactionAspect*;
    
}
