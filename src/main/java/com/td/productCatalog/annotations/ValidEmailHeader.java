package com.td.productCatalog.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailHeaderValidator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailHeader {

    String message() default "Invalid email header value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
