package com.sweethome.bookingservice.service;

import com.sweethome.bookingservice.dto.BookingInfoDTO;
import com.sweethome.bookingservice.dto.BookingRequestDTO;
import com.sweethome.bookingservice.dto.PaymentRequestDTO;

import java.io.IOException;


/**
 * Service Interfaces exposed to controller layer
 */
public interface BookingService {

    /**
     * Handles booking request from user and returns booking availability and details.
     *
     * @param bookingRequestDTO
     * @return BookingInfoDTO
     */
    BookingInfoDTO createBooking(BookingRequestDTO bookingRequestDTO);

    /**
     * Handles payment request from user and returns booking details by updating transaction ID.
     *
     * @param bookingId
     * @param paymentRequestDTO
     * @return BookingInfoDTO
     * @throws IOException
     */
    BookingInfoDTO processBooking(int bookingId, PaymentRequestDTO paymentRequestDTO) throws IOException;
}
