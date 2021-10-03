package com.sweethome.bookingservice.controller;

import com.sweethome.bookingservice.dto.BookingInfoDTO;
import com.sweethome.bookingservice.dto.BookingRequestDTO;
import com.sweethome.bookingservice.dto.PaymentRequestDTO;
import com.sweethome.bookingservice.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Booking Controller - Manages below URI endpoints
 * URI 1 : /booking
 * URI 2 : /booking/{bookingId}/transaction
 */
@RestController
public class BookingController {

    private BookingService bookingService;

    private ModelMapper modelMapper;

    @Autowired
    public BookingController(BookingService bookingService, ModelMapper modelMapper) {
        this.bookingService = bookingService;
        this.modelMapper = modelMapper;
    }

    /**
     * Endpoint allowing user to fetch booking availability details.
     * <p>
     * URI: /booking
     * HTTP METHOD: POST
     * RequestBody: fromDate, toDate,aadharNumber,numOfRooms
     * <p>
     * Response Status: Created
     * Response: ResponseEntity<BookingInfoEntity>
     *
     * @param bookingRequestDTO
     * @return ResponseEntity<BookingInfoDTO>
     */
    @PostMapping(value = "/booking", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingInfoDTO> makeBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {

        BookingInfoDTO savedBookingInfoDto = bookingService.createBooking(bookingRequestDTO);

        return new ResponseEntity<>(savedBookingInfoDto, HttpStatus.CREATED);
    }

    /**
     * Endpoint allowing user to confirm & make payment for available booking as fetched through "/booking" endpoint
     * <p>
     * URL: booking/{bookingId}/transaction
     * HTTP METHOD: POST
     * PathVariable: int
     * RequestBody: paymentMode, bookingId,upiId,cardNumber
     * <p>
     * Response Status: Created
     * Response: ResponseEntity<BookingInfoEntity>
     *
     * @param bookingId
     * @param paymentRequestDTO
     * @return
     * @throws IOException
     */
    @PostMapping(value = "booking/{bookingId}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookingInfoDTO> processBookingPayment(
            @PathVariable(value = "bookingId") int bookingId, @Valid @RequestBody PaymentRequestDTO paymentRequestDTO)
            throws IOException {

        BookingInfoDTO savedBookingInfoDto = bookingService.processBooking(bookingId, paymentRequestDTO);

        return new ResponseEntity<>(savedBookingInfoDto, HttpStatus.CREATED);
    }
}
