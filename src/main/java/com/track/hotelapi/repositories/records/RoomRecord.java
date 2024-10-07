package com.track.hotelapi.repositories.records;

import lombok.*;

@Value
public class RoomRecord{
    long id;
    @NonNull String name;
    long hotelId;
}