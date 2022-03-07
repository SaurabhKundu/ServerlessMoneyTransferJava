package com.mobiquity.transfer.advice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.mobiquity.transfer.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class MoneyTransferAdvice {

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<Object> handleGenericException(Exception e) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .body(e.getMessage());
  }

}
