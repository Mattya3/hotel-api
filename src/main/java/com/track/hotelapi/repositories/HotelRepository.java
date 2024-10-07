package com.track.hotelapi.repositories;

import com.track.hotelapi.inputs.*;
import com.track.hotelapi.models.*;
import com.track.hotelapi.exceptions.*;
import com.track.hotelapi.repositories.records.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class HotelRepository{
    private final JdbcTemplate jdbc;

    @Autowired
    public HotelRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    public List<Hotel> listAllHotels(){
        List<HotelRecord> hotelRecords = listAllhotelRecords();
        List<RoomRecord> roomRecords = listAllRoomRecords();
        Map<Long, List<Room>> roomsMap = new HashMap<>();
        for(RoomRecord r: roomRecords){
            long hotelId = r.getHotelId();
            if(!roomsMap.containsKey(hotelId)) roomsMap.put(hotelId, new ArrayList<>());
            roomsMap.get(hotelId).add(new Room(
                r.getId(),
                r.getName()
            ));
        }
        List<Hotel> hotels = new ArrayList<>();
        for(HotelRecord h: hotelRecords){
            hotels.add(new Hotel(
                h.getId(),
                h.getName(),
                h.getPrefecture(),
                roomsMap.getOrDefault(h.getId(), new ArrayList<>())
            ));
        }
        return hotels;
    }

    public Hotel getHotelById(long hotelId){
        HotelRecord hotel = hotelRecordsById(hotelId);
        List<RoomRecord> roomRecords = listAllRoomRecords();
        Map<Long, List<Room>> roomsMap = new HashMap<>();
        for(RoomRecord r: roomRecords){
            long nowHotelId = r.getHotelId();
            if(!roomsMap.containsKey(nowHotelId)) roomsMap.put(nowHotelId, new ArrayList<>());
            roomsMap.get(nowHotelId).add(new Room(
                r.getId(),
                r.getName()
            ));
        }
        return new Hotel(
            hotel.getId(),
            hotel.getName(),
            hotel.getPrefecture(),
            roomsMap.getOrDefault(hotel.getId(), new ArrayList<>()));
    }

    public List<Room> getRoomsByHotelId(long hotelId){
        List<RoomRecord> roomRecords = listRoomRecordsByHotelId(hotelId);
        List<Room> rooms = new ArrayList<>();
        for(RoomRecord r: roomRecords){
            rooms.add(new Room(
                r.getId(),
                r.getName()
            ));
        }
        return rooms;
    }

    public Room getRoomByBothId(long hotelId, long roomId){
        if(!checkHotelId(hotelId)) throw new HotelIdException();
        RoomRecord roomRecord = getRoomRecordByBothId(hotelId, roomId);
        return new Room(
            roomRecord.getId(),
            roomRecord.getName()
        );
    }

    public Hotel createHotel(HotelInput hotel){
        SqlRowSet rs = jdbc.queryForRowSet("INSERT INTO hotel (id, name, prefecture) VALUES(nextval('hibernate_sequence'), ?, ?) RETURNING *", hotel.getName(), hotel.getPrefecture());
        rs.next();
        HotelRecord updateHotel = new HotelRecord(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("prefecture")
        );
        return new Hotel(
            updateHotel.getId(),
            updateHotel.getName(),
            updateHotel.getPrefecture(),
            new ArrayList<>()
        );
    }

    public Room createRoom(long hotelId, RoomInput room){
        if(!checkHotelId(hotelId)) throw new HotelIdException();
        SqlRowSet rs = jdbc.queryForRowSet("INSERT INTO room (id, name, hotel_id) VALUES(nextval('hibernate_sequence'), ?, ?) RETURNING *", room.getName(), hotelId);
        rs.next();
        RoomRecord updateRoom = new RoomRecord(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getLong("hotel_id")
        );
        return new Room(
            updateRoom.getId(),
            updateRoom.getName()
        );   
    }

    public Hotel updateHotel(long hotelId, HotelInput hotel){
        if(!checkHotelId(hotelId)) throw new HotelIdException();
        SqlRowSet rs = jdbc.queryForRowSet("UPDATE hotel SET name = ?, prefecture = ? WHERE id = ? RETURNING *", hotel.getName(), hotel.getPrefecture(), hotelId);
        if(!rs.next()) throw new HotelIdException();
        return getHotelById(rs.getLong("id"));
    }

    public Room updateRoom(long hotelId, long roomId, RoomInput room){
        if(!checkHotelId(hotelId)) throw new HotelIdException();
        SqlRowSet rs = jdbc.queryForRowSet("UPDATE room SET name = ? WHERE id = ? AND hotel_id = ? RETURNING *", room.getName(), roomId, hotelId);
        if(!rs.next()) throw new RoomIdException();
        RoomRecord updateRoom = new RoomRecord(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getLong("hotel_id")
        );
        return new Room(
            updateRoom.getId(),
            updateRoom.getName()
        ); 
    }

    public void deleteHotel(long hotelId){
        String sql = "DELETE FROM hotel WHERE id = ? RETURNING *";
        SqlRowSet rs = jdbc.queryForRowSet(sql, hotelId);
        if(!rs.next()) throw new HotelIdException();
        String sql2 = "DELETE FROM room WHERE hotel_id = ? RETURNING *";
        jdbc.queryForRowSet(sql2, hotelId);
    }

    public void deleteRoom(long hotelId, long roomId){
        if(!checkHotelId(hotelId)) throw new HotelIdException();
        Sting sql = "DELETE FROM room WHERE id = ? AND hotel_id = ? RETURNING *";
        SqlRowSet rs = jdbc.queryForRowSet(sql, roomId, hotelId);
        if(!rs.next()) throw new RoomIdException();
    }

    private List<HotelRecord> listAllhotelRecords(){
        List<HotelRecord> list = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM hotel");
        while(rs.next()){
            list.add(new HotelRecord(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("prefecture")
            ));
        }
        return list;
    }

    private List<RoomRecord> listAllRoomRecords(){
        List<RoomRecord> list = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM room");
        while(rs.next()){
            list.add(new RoomRecord(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("hotel_id")
            ));
        }
        return list;
    }

    private HotelRecord hotelRecordsById(long hotelId){
        String sql = "SELECT * FROM hotel WHERE id = ?";
        SqlRowSet rs = jdbc.queryForRowSet(sql, hotelId);
        if(!rs.next()) throw new HotelIdException();
        return new HotelRecord(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("prefecture")
        );
    }

    private List<RoomRecord> listRoomRecordsByHotelId(long hotelId){
        List<RoomRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM room WHERE hotel_id = ?";
        SqlRowSet rs = jdbc.queryForRowSet(sql, hotelId);
        while(rs.next()){
            list.add(new RoomRecord(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("hotel_id")
            ));
        }
        if(list.size() == 0) throw new HotelIdException();
        return list;
    }

    private RoomRecord getRoomRecordByBothId(long hotelId, long roomId){
        String sql = "SELECT * FROM room WHERE id = ? AND hotel_id = ?";
        SqlRowSet rs = jdbc.queryForRowSet(sql, roomId, hotelId);
        if(!rs.next()) throw new RoomIdException();
        return new RoomRecord(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getLong("hotel_id")
        );
    }

    private boolean checkHotelId(long hotelId){
        String sql = "SELECT COUNT(*) AS cnt FROM hotel WHERE id = ?";
        SqlRowSet rs = jdbc.queryForRowSet(sql, hotelId);
        rs.next();
        return rs.getLong("cnt") != 0;
    }
}
