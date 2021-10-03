package com.sweethome.paymentservice.dto;

import com.sweethome.paymentservice.constants.Messages;
import com.sweethome.paymentservice.customvalidators.CustomPaymentModeValidator;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * DTO containing payment transaction details as requested part of "/transaction" endpoint.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@CustomPaymentModeValidator
public class TransactionRequestDto {

    @NotNull(message = Messages.INVALID_BOOKING_ID)
    private Integer bookingId;

    @NotBlank(message = Messages.INVALID_MODE_OF_PAYMENT)
    private String paymentMode;

    private String upiId;

    private String cardNumber;

    // Ensures that card details are stored repo in lower case as per assignment expectations in schema pdf.
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode.toLowerCase();
    }

}
