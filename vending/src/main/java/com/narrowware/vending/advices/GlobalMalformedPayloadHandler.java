package com.narrowware.vending.advices;

import com.narrowware.vending.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalMalformedPayloadHandler {

    // Might consider using TranslationService here
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException exception) {
        final BindingResult br = exception.getBindingResult();
        final String message = br.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Payload validation failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    // As this is more like a slap on the hand for the user, we might not be so harsh.
    // Idea - return a Notifications object, containing different levels of notifications
    // Depending on severity, we could use them for a UI static banner
    // The UI can also have validation
    @ExceptionHandler(ExceedingMaximumVendingSpaceException.class)
    public ResponseEntity<String> handleException(ExceedingMaximumVendingSpaceException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidIdentifierException.class)
    public ResponseEntity<String> handleException(InvalidIdentifierException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(MachineNeedsMaintenanceException.class)
    public ResponseEntity<String> handleException(MachineNeedsMaintenanceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleException(InsufficientFundsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ItemDoesNotContainPriceException.class)
    public ResponseEntity<String> handleException(ItemDoesNotContainPriceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
