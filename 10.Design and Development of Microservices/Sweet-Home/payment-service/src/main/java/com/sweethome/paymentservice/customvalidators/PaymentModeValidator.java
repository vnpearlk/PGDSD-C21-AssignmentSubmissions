package com.sweethome.paymentservice.customvalidators;


import com.sweethome.paymentservice.constants.PaymentMode;
import com.sweethome.paymentservice.dto.TransactionRequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Validates requirements as applicable for payment modes.
 */
public class PaymentModeValidator implements ConstraintValidator<CustomPaymentModeValidator, TransactionRequestDto> {
    @Override
    public void initialize(CustomPaymentModeValidator constraintAnnotation) {
    }

    /**
     * Validates following -
     * 1. Whether PaymentMode is any of payment modes, as supported in {@class PaymentMode}. (UPI or CARD)
     * 2. Checks if PaymentMode is UPI then UpiID is not Empty. Similarly, CardNumber should not be empty for CARD mode.
     * <p>
     * Returns true, only if all above conditions satisfy; else false.
     *
     * @param transactionRequestDto
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(TransactionRequestDto transactionRequestDto,
                           ConstraintValidatorContext constraintValidatorContext) {

        if (transactionRequestDto.getBookingId() == null)
            return false;

        String paymentMode = transactionRequestDto.getPaymentMode();

        if (paymentMode == null || paymentMode.isEmpty())
            return false;

        String upiId = transactionRequestDto.getUpiId();
        String cardNumber = transactionRequestDto.getCardNumber();

        if (paymentMode.equalsIgnoreCase(PaymentMode.UPI.name())) {
            return upiId != null && !upiId.isEmpty();
        } else if (paymentMode.equalsIgnoreCase(PaymentMode.CARD.name())) {
            return cardNumber != null && !cardNumber.isEmpty();
        }

        return false;
    }
}

