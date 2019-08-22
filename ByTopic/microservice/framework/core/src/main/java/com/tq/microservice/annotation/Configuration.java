package com.tq.microservice.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
// TODO support TYPE
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
  String service() default "";
  String serviceVersion() default "";
  String name() default "";
}
