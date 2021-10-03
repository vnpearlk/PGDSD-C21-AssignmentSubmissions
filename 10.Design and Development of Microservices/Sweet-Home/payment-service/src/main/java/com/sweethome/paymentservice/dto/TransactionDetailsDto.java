package com.sweethome.paymentservice.dto;

import lombok.*;


/**
 * DTO containing transaction details to be sent as response for endpoint "/transaction/{transactionId}"
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDetailsDto {

    private int transactionId;

    private String paymentMode;

    private Integer bookingId;

    private String upiId;

    private String cardNumber;

    // Ensure that response object matches the expected upper case payment mode usage as per assignment expectations.
    public void setPaymentMode(String paymentMode){
        this.paymentMode = paymentMode.toUpperCase();
    }
}
