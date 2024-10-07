package com.track.hotelapi.controllers;


import java.util.*;
import org.springframework.ui.Model;
import com.track.hotelapi.models.*;
import com.track.hotelapi.inputs.*;
import com.track.hotelapi.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
@RequestMapping("/hotel")
public class HotelMVCController{

    private final HotelService service;

    @Autowired
    public HotelMVCController(HotelService service){
        this.service = service;
    }

    @GetMapping("/list")
    public String hotelList(Model model){
        List<Hotel> hotels = service.listAllHotels();
        model.addAttribute("hotels", hotels);
        return "list";
    }

    @GetMapping("/register")
    public String hotelRegister(Model model){
        return "register";
    }

    @PostMapping("/register")
    public String hotetGetRegister(@RequestParam String name, @RequestParam String prefecture){
        HotelInput hotelInput = new HotelInput(name, prefecture);
        service.createHotel(hotelInput);
        return "redirect:/hotel/list";
    }
}

