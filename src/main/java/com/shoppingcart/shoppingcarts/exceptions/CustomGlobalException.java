package com.shoppingcart.shoppingcarts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shoppingcart.shoppingcarts.response.ApiResponse;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;


@ControllerAdvice
public class CustomGlobalException {

    // Inject the error message dynamically using @Value
    @Value("${error.generalMessage}")
    private String generalErrorMessage;

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<CustomErrorHandler> handleCartNotFoundException(CartNotFoundException e) {
        CustomErrorHandler errorResponse = new CustomErrorHandler(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                getStackTrace(e)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    private String getStackTrace(Exception ex) {
        StringBuilder stackTrace = new StringBuilder();
        StackTraceElement[] stackTraceElements = ex.getStackTrace();

        // Limit to 10 lines of stack trace
        int limit = Math.min(stackTraceElements.length, 10);
        for (int i = 0; i < limit; i++) {
            stackTrace.append(stackTraceElements[i].toString()).append("\n");
        }
        return stackTrace.toString();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorHandler> handleGenericException(Exception ex) {
        CustomErrorHandler errorResponse = new CustomErrorHandler(
                generalErrorMessage,
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                getStackTrace(ex)
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(InvalidRequest.class)
    public ResponseEntity<CustomErrorHandler> invalidPayload (InvalidRequest e) {
        CustomErrorHandler errorResponse = new CustomErrorHandler(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                getStackTrace(e)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse("Unauthorized access: " + ex.getMessage(), null));
    }
}
