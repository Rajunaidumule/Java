package com.td.productCatalog.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.regex.Pattern;

public class EmailValidation implements ConstraintValidator<EmailConstraint, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.isNull(email)){
            return false;
        }
        else{
            System.out.println("Validating email: " + email);
            Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
            return emailPattern.matcher(email).matches();
        }
    }
}
