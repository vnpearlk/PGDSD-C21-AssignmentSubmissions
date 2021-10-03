package com.sweethome.paymentservice.mapper;

import com.sweethome.paymentservice.dto.TransactionRequestDto;
import com.sweethome.paymentservice.entity.TransactionDetailsEntity;


/**
 * Mapper util
 */
public class PaymentMapper {

    /**
     * Mapper util to map TransactionRequestDto & TransactionDetailsEntity
     *
     * @param transactionRequestDto
     * @return
     */
    public static TransactionDetailsEntity convertTransactionReqDtoToDetailsEntity(
            TransactionRequestDto transactionRequestDto) {

        TransactionDetailsEntity transactionDetailsEntity = TransactionDetailsEntity.builder()
                .bookingId(transactionRequestDto.getBookingId())
                .paymentMode(transactionRequestDto.getPaymentMode())
                .upiId(transactionRequestDto.getUpiId())
                .cardNumber(transactionRequestDto.getCardNumber())
                .build();

        return transactionDetailsEntity;
    }
}
