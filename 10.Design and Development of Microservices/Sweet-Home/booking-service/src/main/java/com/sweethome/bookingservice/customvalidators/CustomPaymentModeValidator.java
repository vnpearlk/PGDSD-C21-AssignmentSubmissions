package com.sweethome.bookingservice.customvalidators;

import com.sweethome.bookingservice.constants.Messages;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Class Constraint validator to validate payment request details from user.
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PaymentModeValidator.class})
public @interface CustomPaymentModeValidator {

    String message() default Messages.INVALID_MODE_OF_PAYMENT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
