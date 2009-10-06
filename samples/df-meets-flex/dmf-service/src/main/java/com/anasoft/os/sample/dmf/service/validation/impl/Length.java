package com.anasoft.os.sample.dmf.service.validation.impl;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Length {

    int min() default 0;
    
    boolean minInclusive() default true;
    
    int max() default Integer.MAX_VALUE;
    
    boolean maxInclusive() default true;
    
}
