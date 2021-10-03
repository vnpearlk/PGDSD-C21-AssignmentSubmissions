package com.sweethome.paymentservice.entity;

import lombok.*;

import javax.persistence.*;


/**
 * Entity class that stores transaction details for a particular bookingId.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "transaction")
public class TransactionDetailsEntity {

    @Id
    @Column(name = "transactionId", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionId;

    @Column(name = "paymentMode")
    private String paymentMode;

    @Column(name = "bookingId", nullable = false)
    private Integer bookingId;

    @Column(name = "upiId")
    private String upiId;

    @Column(name = "cardNumber")
    private String cardNumber;

    // Ensures that card details are stored repo in lower case as per assignment expectations in schema pdf.
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode.toLowerCase();
    }
}
