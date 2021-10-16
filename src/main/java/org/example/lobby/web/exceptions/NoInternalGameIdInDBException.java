package org.example.lobby.web.exceptions;

public class NoInternalGameIdInDBException extends RuntimeException {

  public NoInternalGameIdInDBException(String message) {
    super(message);
  }
}
