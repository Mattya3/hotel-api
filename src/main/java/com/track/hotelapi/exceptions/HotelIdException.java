package com.track.hotelapi.exceptions;

public class HotelIdException extends RuntimeException {
  public HotelIdException(String message) {
    super(message);
  }

  public HotelIdException() {
    super();
  }

  public HotelIdException(String message, Throwable cause) {
    super(message, cause);
  }
}

