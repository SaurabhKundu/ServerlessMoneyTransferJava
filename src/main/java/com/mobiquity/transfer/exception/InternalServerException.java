package com.mobiquity.transfer.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException {

  public InternalServerException(String message) {
    super(message);
  }
}
