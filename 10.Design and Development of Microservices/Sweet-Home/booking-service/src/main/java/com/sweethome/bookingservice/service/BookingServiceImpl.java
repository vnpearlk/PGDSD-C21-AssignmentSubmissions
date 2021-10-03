package com.sweethome.bookingservice.service;

import com.sweethome.bookingservice.constants.Messages;
import com.sweethome.bookingservice.constants.PaymentMode;
import com.sweethome.bookingservice.dto.BookingInfoDTO;
import com.sweethome.bookingservice.dto.BookingRequestDTO;
import com.sweethome.bookingservice.dto.PaymentRequestDTO;
import com.sweethome.bookingservice.entity.BookingInfoEntity;
import com.sweethome.bookingservice.exceptions.InvalidRequestException;
import com.sweethome.bookingservice.config.KafkaConfig;
import com.sweethome.bookingservice.repo.BookingInfoRepo;
import com.sweethome.bookingservice.utils.BookingUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * Booking Service Implementation
 */
@Service
public class BookingServiceImpl implements BookingService {

    @Value("${paymentTransaction.url}")
    private String paymentTransactionUrl;

    @Value("${transactionDetails.url}")
    private String transactionDetailsUrl;

    private BookingInfoRepo bookingInfoRepo;

    private ModelMapper modelMapper;

    private RestTemplate restTemplate;

    private KafkaConfig kafkaConfig;

    @Autowired
    public BookingServiceImpl(BookingInfoRepo bookingInfoRepo, ModelMapper modelMapper,
                              RestTemplate restTemplate, KafkaConfig kafkaConfig) {
        this.bookingInfoRepo = bookingInfoRepo;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.kafkaConfig = kafkaConfig;
    }

    /**
     * Handles booking request from user returns booking availability and details.
     *
     * @param bookingRequestDTO
     * @return BookingInfoDTO
     */
    @Override
    public BookingInfoDTO createBooking(BookingRequestDTO bookingRequestDTO) {

        BookingInfoEntity bookingInfoEntity = modelMapper.map(bookingRequestDTO, BookingInfoEntity.class);

        // Generate Random room numbers within range of 0 to 100
        bookingInfoEntity.setRoomNumbers(
                BookingUtils.getRandomNumbers(
                        bookingInfoEntity.getNumOfRooms()).stream().collect(Collectors.joining(","))
        );

        // Calculate Room price
        bookingInfoEntity.setRoomPrice(
                BookingUtils.getRoomPrice(bookingInfoEntity.getFromDate(), bookingInfoEntity.getToDate(),
                        bookingInfoEntity.getNumOfRooms()));

        Date currentDate = Calendar.getInstance().getTime();
        bookingInfoEntity.setBookedOn(currentDate);

        // persist the booking info data by sending it to Repo layer.
        BookingInfoEntity savedBookingInfoEntity = bookingInfoRepo.save(bookingInfoEntity);

        BookingInfoDTO savedBookingInfoDto = modelMapper.map(savedBookingInfoEntity, BookingInfoDTO.class);

        return savedBookingInfoDto;
    }

    /**
     * Handles payment request from user and returns booking details by updating transaction ID.
     *
     * @param bookingId
     * @param paymentRequestDTO
     * @return BookingInfoDTO
     * @throws IOException
     */
    @Override
    public BookingInfoDTO processBooking(int bookingId, PaymentRequestDTO paymentRequestDTO) throws IOException {

        // Additional check, to ensure that bookingId passed through the endpoint URI matches the bookingID
        // within the request body.
        if (bookingId != paymentRequestDTO.getBookingId())
            throw new InvalidRequestException(Messages.INVALID_BOOKING_ID);

        // Verify booking ID, if no booking request ID is present in DB, then throw invalid request exception
        // response with message "Invalid Booking Id"
        BookingInfoEntity bookingInfoEntity =
                bookingInfoRepo.findById(bookingId)
                        .orElseThrow(() -> new InvalidRequestException(Messages.INVALID_BOOKING_ID));

        // Configure payment Request DTO to ensure that card is null for UPI mode & vice versa.
        String paymentMode = paymentRequestDTO.getPaymentMode();
        if (paymentMode.equalsIgnoreCase(PaymentMode.CARD.name())) {
            paymentRequestDTO.setUpiId(null);
        } else if (paymentMode.equalsIgnoreCase(PaymentMode.UPI.name())) {
            paymentRequestDTO.setCardNumber(null);
        }

        // Using Integer to avoid NPE while unboxing int warning, upon RestTemplate.postForObject() invocation.
        Integer transactionId = 0;

        // Using RestTemplate for synchronous communication with Paymentservice, discovered
        // through the Eureka service discovery
        transactionId = restTemplate.postForObject(paymentTransactionUrl, paymentRequestDTO, Integer.class);

        if (transactionId != null && transactionId != 0) {
            bookingInfoEntity.setTransactionId(transactionId);
            bookingInfoEntity = bookingInfoRepo.save(bookingInfoEntity);
        }

        BookingInfoDTO savedBookingInfoDto = modelMapper.map(bookingInfoEntity, BookingInfoDTO.class);

        // Compose kafka message, as is as expected form assignment.
        String message = "Booking confirmed for user with aadhaar number: " + bookingInfoEntity.getAadharNumber() +
                "    |    " + "Here are the booking details:    " + bookingInfoEntity.toString();

        // Publish/produce kafka message
        kafkaConfig.publish("message", "notification", message);

        return savedBookingInfoDto;
    }
}
