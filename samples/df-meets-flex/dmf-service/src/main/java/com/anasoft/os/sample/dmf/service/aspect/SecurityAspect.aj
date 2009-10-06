package com.anasoft.os.sample.dmf.service.aspect;

import org.springframework.security.annotation.Secured;
import org.springframework.security.intercept.method.aspectj.AspectJCallback;
import org.springframework.security.intercept.method.aspectj.AspectJSecurityInterceptor;

public aspect SecurityAspect {

    private pointcut executionOfAnyPublicMethodInSecuredTypeOrSubtypes():
        execution(public * ((@Secured *)+).*(..));
    
    private pointcut executionOfSecuredMethod():
        execution(public * *(..)) && @annotation(Secured);
    
    private pointcut securityScope():
        LayerDefinitionAspect.inServiceLayer();
    
    private pointcut securedPointcut():
        (executionOfAnyPublicMethodInSecuredTypeOrSubtypes() || executionOfSecuredMethod())
        && securityScope();
    
    private AspectJSecurityInterceptor securityInterceptor;
    
    Object around(): securedPointcut() {
        if (securityInterceptor == null)
            throw new IllegalStateException("SecurityInterceptor is required");
        
        AspectJCallback callback = new AspectJCallback() {
            public Object proceedWithObject() {
                return proceed();
            }
        };
        
        return securityInterceptor.invoke(thisJoinPoint, callback);
    }
    
    public void setSecurityInterceptor(AspectJSecurityInterceptor securityInterceptor) {
        this.securityInterceptor = securityInterceptor;
    }
    
}
