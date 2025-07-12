package com.purefunction.ezbt.accommodation.controller;

import com.purefunction.ezbt.accommodation.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/cities")
    public List<?> getByCity(String cityName) {
        return null; // Fixed: Added return value
    }

    @GetMapping()
    public List<?> getByLocation(double latitude,
                                 double longitude) {
        return null; // Fixed: Added return value
    }
}
