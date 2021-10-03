package com.sweethome.bookingservice.exceptions.handler;

import com.sweethome.bookingservice.constants.Messages;
import com.sweethome.bookingservice.exceptions.InvalidRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Custom exception handler to handle error states & notify error in user readable form.
 * eg.,
 * {
 * "message": "Invalid mode of payment", (or) "Invalid booking Id"
 * "statusCode": 400
 * }
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex, WebRequest req) {
        Map<String, Object> body = new LinkedHashMap<>();

        // Not all the error messages are retrieved as it is expected from assignment requirement that the
        // only one error message is displayed, and not as list of all error messages, like below
        //  {
        //      "message": "Invalid mode of payment",
        //      "statusCode": 400
        //  }
        String errorMessage = ex.getLocalizedMessage();

        body.put("message", errorMessage);
        body.put("statusCode", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();

        // Not all the error messages are retrieved as it is expected from assignment requirement that the
        // only one error message is displayed, and not as list of all error messages, like below
        //  {
        //      "message": "Invalid mode of payment",
        //      "statusCode": 400
        //  }
        //
        //  In addition, Exceptions triggered from custom Class Constraint Validator {@code CustomPaymentModeValidator}
        // check failures are not retrievable through getFieldErrors, while all the Global & Field errors
        // i.e. Field errors, Class level validation errors are retrievable through getAllErrors().
        String errorMessage = ex.getBindingResult().getAllErrors().
                stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(Messages.INVALID_MODE_OF_PAYMENT);

        body.put("message", errorMessage);
        body.put("statusCode", httpStatus.value());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
