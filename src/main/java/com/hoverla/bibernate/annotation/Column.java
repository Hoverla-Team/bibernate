package com.hoverla.bibernate.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    boolean primaryKey() default false;
    String name();
}
