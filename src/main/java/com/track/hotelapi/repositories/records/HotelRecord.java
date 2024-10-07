package com.track.hotelapi.repositories.records;

import lombok.*;

@Value
public class HotelRecord{
    long id;
    @NonNull String name;
    @NonNull String prefecture;
}