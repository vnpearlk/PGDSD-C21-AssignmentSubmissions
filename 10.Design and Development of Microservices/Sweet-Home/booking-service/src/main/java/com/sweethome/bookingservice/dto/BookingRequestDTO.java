package com.sweethome.bookingservice.dto;

import lombok.*;

import java.util.Date;


/**
 * DTO containing user requested params checking for room booking availability.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingRequestDTO {

    private Date fromDate;

    private Date toDate;

    private String aadharNumber;

    private int numOfRooms;

}
