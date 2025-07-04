package com.purefunction.ezbt.room.controller;

import com.purefunction.ezbt.room.client.agoda.dto.AgodaRequest;
import com.purefunction.ezbt.room.dto.RoomResponse; // RoomResponse import
import com.purefunction.ezbt.room.mapper.RoomMapper; // RoomMapper import
import com.purefunction.ezbt.room.model.Room;
import com.purefunction.ezbt.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomResponse> searchRooms(@ModelAttribute AgodaRequest request) {
        List<Room> rooms = roomService.searchRooms(request);
        return RoomMapper.toRoomResponses(rooms);
    }

    @GetMapping("/cities")
    public List<String> searchCities(@RequestParam String city) {
        return roomService.searchCities(city);
    }
}
