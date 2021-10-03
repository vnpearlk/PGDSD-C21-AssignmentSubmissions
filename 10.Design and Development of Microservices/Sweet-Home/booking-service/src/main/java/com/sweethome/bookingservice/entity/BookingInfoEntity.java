package com.sweethome.bookingservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


/**
 * Entity class that stores/tracks booking availability info as requested by user.
 * The {@code transactionId} will be set to 0, by default. {@code transactionId} gets updated
 * to valid transaction ID, once user initiates payment transaction (through endpoint "booking/{bookingId}/transaction")
 * & makes a successful payment.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "booking")
public class BookingInfoEntity {
    @Id
    @Column(name = "bookingId", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fromDate")
    private Date fromDate;

    @Column(name = "toDate")
    private Date toDate;

    // Its been confirmed offline with Instructor that Schema referring to aadharNumber field to be unique is an error
    // & hence haven't set unique constraint to true for this field.
    @Column(name = "aadharNumber")
    private String aadharNumber;

    @Column(name = "numOfRooms")
    private int numOfRooms;

    @Column(name = "roomNumbers")
    private String roomNumbers;

    @Column(name = "roomPrice", nullable = false)
    private int roomPrice;

    @Column(name = "transactionId", columnDefinition = "integer default 0")
    private int transactionId;

    @Column(name = "bookedOn")
    private Date bookedOn;
}
