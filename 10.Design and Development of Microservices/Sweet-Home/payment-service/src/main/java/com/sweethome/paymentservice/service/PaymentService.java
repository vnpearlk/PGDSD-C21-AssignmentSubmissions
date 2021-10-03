package com.sweethome.paymentservice.service;

import com.sweethome.paymentservice.dto.TransactionDetailsDto;
import com.sweethome.paymentservice.dto.TransactionRequestDto;


/**
 * Service Interfaces exposed to controller layer
 */
public interface PaymentService {

    /**
     * Handles make payment request from booking service and returns transaction ID
     *
     * @param transactionRequestDto
     * @return transactionId
     */
    int makePayment(TransactionRequestDto transactionRequestDto);

    /**
     * Handles request to fetch transaction details of a particular transaction.
     *
     * @param transactionID
     * @return TransactionDetailsDto
     */
    TransactionDetailsDto fetchTransaction(int transactionID);
}
