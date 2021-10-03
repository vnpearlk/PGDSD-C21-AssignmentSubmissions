package com.sweethome.paymentservice.service;

import com.sweethome.paymentservice.constants.Messages;
import com.sweethome.paymentservice.constants.PaymentMode;
import com.sweethome.paymentservice.dto.TransactionDetailsDto;
import com.sweethome.paymentservice.dto.TransactionRequestDto;
import com.sweethome.paymentservice.entity.TransactionDetailsEntity;
import com.sweethome.paymentservice.exception.TransactionNotFoundException;
import com.sweethome.paymentservice.mapper.PaymentMapper;
import com.sweethome.paymentservice.repo.TransactionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {

    private TransactionRepo transactionRepo;

    private ModelMapper modelMapper;

    @Autowired
    public PaymentServiceImpl(TransactionRepo transactionRepo, ModelMapper modelMapper) {
        this.transactionRepo = transactionRepo;
        this.modelMapper = modelMapper;
    }

    /**
     * Handles make payment request from booking service and returns transaction ID
     *
     * @param transactionRequestDto
     * @return transactionId
     */
    @Override
    public int makePayment(TransactionRequestDto transactionRequestDto) {

        TransactionDetailsEntity transactionDetailsEntity =
                PaymentMapper.convertTransactionReqDtoToDetailsEntity(transactionRequestDto);

        // ensure that card is null for UPI mode, similarly upiId to null for CARD mode.
        String paymentMode = transactionDetailsEntity.getPaymentMode();
        if (paymentMode.equalsIgnoreCase(PaymentMode.CARD.name())) {
            transactionDetailsEntity.setUpiId(null);
        } else if (paymentMode.equalsIgnoreCase(PaymentMode.UPI.name())) {
            transactionDetailsEntity.setCardNumber(null);
        }

        transactionDetailsEntity = transactionRepo.save(transactionDetailsEntity);

        int transactionID = transactionDetailsEntity.getTransactionId();

        return transactionID;
    }

    /**
     * Handles request to fetch transaction details of a particular transaction.
     *
     * @param transactionID
     * @return TransactionDetailsDto
     */
    @Override
    public TransactionDetailsDto fetchTransaction(int transactionID) {

        TransactionDetailsEntity transactionDetailsEntity = transactionRepo.findById(transactionID)
                .orElseThrow(
                        () -> new TransactionNotFoundException("No Transaction with id[" + transactionID + "] found")
                );

        TransactionDetailsDto savedTransactionDetailsDto =
                modelMapper.map(transactionDetailsEntity, TransactionDetailsDto.class);

        return savedTransactionDetailsDto;
    }
}
