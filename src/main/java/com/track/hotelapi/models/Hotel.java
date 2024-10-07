package com.track.hotelapi.models;

import lombok.*;
import java.util.*;

@Value
public class Hotel{
    long id;
    @NonNull String name, prefecture;
    @NonNull List<Room> rooms;
}