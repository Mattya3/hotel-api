package com.track.hotelapi.controllers;

import com.track.hotelapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.track.hotelapi.models.*;
import com.track.hotelapi.inputs.*;
import com.track.hotelapi.exceptions.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
public class HotelController{
    private final HotelService service;

    @Autowired
    public HotelController(HotelService service){
        this.service = service;
    }

    @GetMapping("")    
    List<Hotel> listHotels(){
        List<Hotel> hotels = service.listAllHotels();
        return hotels;
    }

    @GetMapping("/{hotelId}")
    Hotel getHotel(@PathVariable("hotelId") long hotelId){
        Hotel hotel = service.getHotelById(hotelId);
        return hotel;
    }

    @GetMapping("/{hotelId}/rooms")
    List<Room> getRooms(@PathVariable("hotelId") long hotelId){
        List<Room> rooms = service.listRoomsByHotelId(hotelId);
        return rooms;
    }

    @GetMapping("/{hotelId}/rooms/{roomId}")
    Room getRoom(@PathVariable("hotelId") long hotelId, @PathVariable("roomId") long roomId){
        Room rooms = service.getRoomByBothId(hotelId, roomId);
        return rooms;
    }

    @PostMapping("")
    Hotel createHotel(@RequestBody HotelInput hotel){
        Hotel hotels = service.createHotel(hotel);
        return hotels;
    }

    @PostMapping("/{hotelId}/rooms")
    Room createRoom(@PathVariable("hotelId") long hotelId, @RequestBody RoomInput room){
        Room rooms = service.createRoom(hotelId, room);
        return rooms;
    }

    @PutMapping("/{hotelId}")
    Hotel updateHotel(@PathVariable("hotelId") long hotelId, @RequestBody HotelInput hotel){
        Hotel hotels = service.updateHotel(hotelId, hotel);
        return hotels;
    }

    @PutMapping("/{hotelId}/rooms/{roomId}")
    Room updateRoom(@PathVariable("hotelId") long hotelId, @PathVariable("roomId") long roomId, @RequestBody RoomInput room){
        Room rooms = service.updateRoom(hotelId, roomId, room);
        return rooms;
    }

    @DeleteMapping("/{hotelId}")
    void deleteHotel(@PathVariable("hotelId") long hotelId){
        service.deleteHotel(hotelId);
    }

    @DeleteMapping("/{hotelId}/rooms/{roomId}")
    void deleteRoom(@PathVariable("hotelId") long hotelId, @PathVariable("roomId") long roomId){
        service.deleteRoom(hotelId, roomId);
    }
}