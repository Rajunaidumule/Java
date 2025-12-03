package com.td.productCatalog.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {EmailValidation.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface  EmailConstraint {

    // we must need to have dependecy in build.gradle for custom annotation validation ->

    String message() default "Invalid email format";

    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
