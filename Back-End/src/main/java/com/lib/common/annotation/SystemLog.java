package com.lib.common.annotation;

import java.lang.annotation.*;

/**
 * Sys log annotation
 *  method that mark by this annotation would be blocked by AOP and record
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    String value() default "";
}
