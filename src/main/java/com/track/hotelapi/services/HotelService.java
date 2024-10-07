package com.track.hotelapi.services;

import com.track.hotelapi.inputs.*;
import com.track.hotelapi.models.*;
import com.track.hotelapi.repositories.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class HotelService{
    private final HotelRepository repository;

    @Autowired
    public HotelService(HotelRepository repository){
        this.repository = repository;
    }

    public List<Hotel> listAllHotels(){
        return repository.listAllHotels();
    }

    public Hotel getHotelById(long hotelId){
        return repository.getHotelById(hotelId);
    }

    public List<Room> listRoomsByHotelId(long hotelId){
        return repository.getRoomsByHotelId(hotelId);
    }

    public Room getRoomByBothId(long hotelId, long roomId){
        return repository.getRoomByBothId(hotelId, roomId);
    }

    public Hotel createHotel(HotelInput hotel){
        return repository.createHotel(hotel);
    }

    public Room createRoom(long hotelId, RoomInput room){
        return repository.createRoom(hotelId, room);
    }

    public Hotel updateHotel(long hotelId, HotelInput hotel){
        return repository.updateHotel(hotelId, hotel);
    }

    public Room updateRoom(long hotelId, long roomId, RoomInput room){
        return repository.updateRoom(hotelId, roomId, room);
    }

    public void deleteHotel(long hotelId){
        repository.deleteHotel(hotelId);
    }

    public void deleteRoom(long hotelId, long roomId){
        repository.deleteRoom(hotelId, roomId);
    }
}