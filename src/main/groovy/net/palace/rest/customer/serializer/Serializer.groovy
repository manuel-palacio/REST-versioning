package net.palace.rest.customer.serializer

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import java.lang.annotation.ElementType


@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE])
public @interface Serializer {

    String version() default "v1";

    String format() default "xml";
}