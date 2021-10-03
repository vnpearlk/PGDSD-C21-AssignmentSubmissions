package com.sweethome.bookingservice.dto;

import com.sweethome.bookingservice.constants.Messages;
import com.sweethome.bookingservice.customvalidators.CustomPaymentModeValidator;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * DTO containing payment details from user to make payment for an available booking as
 * shared to user through {@link BookingInfoDTO} response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@CustomPaymentModeValidator
public class PaymentRequestDTO {

    @NotBlank(message = Messages.INVALID_MODE_OF_PAYMENT)
    private String paymentMode;

    @NotNull(message = Messages.INVALID_BOOKING_ID)
    private Integer bookingId;

    private String upiId;

    private String cardNumber;
}
