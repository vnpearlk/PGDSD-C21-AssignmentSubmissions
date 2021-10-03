package com.sweethome.bookingservice.customvalidators;

import com.sweethome.bookingservice.constants.PaymentMode;
import com.sweethome.bookingservice.dto.PaymentRequestDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Validates requirements as applicable for payment modes.
 */
public class PaymentModeValidator implements ConstraintValidator<CustomPaymentModeValidator, PaymentRequestDTO> {
    @Override
    public void initialize(CustomPaymentModeValidator constraintAnnotation) {
    }

    /**
     * Validates following -
     * 1. Whether PaymentMode is any of payment modes, as supported in {@class PaymentMode}. (UPI or CARD)
     * 2. Checks if PaymentMode is UPI then UpiID is not Empty. Similarly, CardNumber should not be empty for CARD mode.
     *
     * Returns true, only if all above conditions satisfy; else false.
     *
     * @param paymentRequestDTO
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(PaymentRequestDTO paymentRequestDTO, ConstraintValidatorContext constraintValidatorContext) {

        if (paymentRequestDTO.getBookingId() == null)
            return false;

        String paymentMode = paymentRequestDTO.getPaymentMode();

        if (paymentMode == null || paymentMode.isEmpty())
            return false;

        String upiId = paymentRequestDTO.getUpiId();
        String cardNumber = paymentRequestDTO.getCardNumber();

        if (paymentMode.equalsIgnoreCase(PaymentMode.UPI.name())) {
            return upiId != null && !upiId.isEmpty();
        } else if (paymentMode.equalsIgnoreCase(PaymentMode.CARD.name())) {
            return cardNumber != null && !cardNumber.isEmpty();
        }

        return false;
    }
}

