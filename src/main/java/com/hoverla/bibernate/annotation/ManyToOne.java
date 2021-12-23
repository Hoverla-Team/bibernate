package com.hoverla.bibernate.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ManyToOne {
    String fk();

    FetchType fetchType() default FetchType.LAZY;
}
