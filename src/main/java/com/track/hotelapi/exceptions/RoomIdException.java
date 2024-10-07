package com.track.hotelapi.exceptions;

public class RoomIdException extends RuntimeException {
  public RoomIdException(String message) {
    super(message);
  }

  public RoomIdException() {
    super();
  }

  public RoomIdException(String message, Throwable cause) {
    super(message, cause);
  }
}