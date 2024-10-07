package com.track.hotelapi.models;

import lombok.*;

@Value
public class Room{
    long id;
    @NonNull String name;
}