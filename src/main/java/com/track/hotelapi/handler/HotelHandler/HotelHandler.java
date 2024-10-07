package com.track.hotelapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import com.track.hotelapi.exceptions.*;
import java.util.*;

@Component
@RestControllerAdvice
public class HotelHandler {

    @ExceptionHandler(HotelIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> hotelIdExceptionHandler(HotelIdException e) {
        Map<String, String> output = new HashMap<>();
        output.put("message", "unknown hotel_id");
        return output;
    }

    @ExceptionHandler(RoomIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> roomIdExceptionHandler(RoomIdException e) {
        Map<String, String> output = new HashMap<>();
        output.put("message", "unknown room_id");
        return output;
    }

}