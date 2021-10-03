package com.sweethome.bookingservice.dto;

import lombok.*;

import java.util.Date;


/**
 * DTO containing Booking availability information sent to user based on the details requested by user
 * through {@link BookingRequestDTO}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingInfoDTO {

    // bookingId - Field named as id, to match assignment naming convention requirement for the response
    private int id;

    private Date fromDate;

    private Date toDate;

    private String aadharNumber;

    private String roomNumbers;

    private int roomPrice;

    private int transactionId;

    private Date bookedOn;
}
