package com.anasoft.os.sample.dmf.service.aspect;

import java.lang.annotation.Annotation;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.anasoft.os.sample.dmf.service.validation.FieldValidator;
import com.anasoft.os.sample.dmf.service.validation.Validated;
import com.anasoft.os.sample.dmf.service.validation.ValidationSupport;

public aspect ValidationAspect extends ValidationSupport implements ApplicationContextAware {

    private pointcut executionOfAnyPublicMethodInValidatedTypeOrSubtypes():
        execution(public * ((@Validated *)+).*(..));
    
    private pointcut executionOfValidatedMethod():
        execution(public * *(..)) && @annotation(Validated);
    
    private pointcut validationScope():
        LayerDefinitionAspect.inServiceLayer();
    
    private pointcut validatedPointcut(Object target):
        (executionOfAnyPublicMethodInValidatedTypeOrSubtypes() || executionOfValidatedMethod())
        && validationScope() && this(target);
    
    private ApplicationContext ctx;
    
    before(Object target): validatedPointcut(target) {
        if (ctx == null)
            throw new IllegalStateException("ApplicationContext is required");
        
        validate(target);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected FieldValidator<Annotation>[] getValidators() {
        return (FieldValidator<Annotation>[]) ctx.getBeansOfType(FieldValidator.class).values().toArray();
    }
    
    @Override
    protected Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
    
    @Override
    protected MessageSource getMessageSource() {
        return ctx;
    }
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
    
}
