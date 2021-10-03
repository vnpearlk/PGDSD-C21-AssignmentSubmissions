package com.sweethome.paymentservice.controller;

import com.sweethome.paymentservice.dto.TransactionDetailsDto;
import com.sweethome.paymentservice.dto.TransactionRequestDto;
import com.sweethome.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Payment Transactions Controller - Manages below URI endpoints
 * URI 1 : /transaction
 * URI 2 : /transaction/{transactionId}
 */
@RestController
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Endpoint allowing booking service to initiate a payment transaction against a booking ID
     * <p>
     * URL: /transaction
     * HTTP METHOD: POST
     * RequestBody: bookingId, paymentMode, upiId, cardNumber
     * <p>
     * Response Status: Created
     * Response: ResponseEntity<transactionId>
     *
     * @param transactionRequestDto
     * @return
     */
    @PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> makeTransaction(@Valid @RequestBody TransactionRequestDto transactionRequestDto) {
        int transactionId = 0;

        transactionId = paymentService.makePayment(transactionRequestDto);

        return new ResponseEntity<>(transactionId, HttpStatus.CREATED);
    }

    /**
     * Endpoint to get transaction related details.
     * <p>
     * URL: /transaction/{transactionId}
     * HTTP METHOD: GET
     * RequestBody: (PathVariable) int
     * <p>
     * Response Status: OK
     * Response: ResponseEntity<TransactionDetailsEntity>
     *
     * @param transactionID
     * @return
     */
    @GetMapping(value = "/transaction/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDetailsDto> getTransactionDetails(
            @PathVariable(value = "transactionId") int transactionID) {

        TransactionDetailsDto response = paymentService.fetchTransaction(transactionID);

        return ResponseEntity.ok(response);
    }
}
