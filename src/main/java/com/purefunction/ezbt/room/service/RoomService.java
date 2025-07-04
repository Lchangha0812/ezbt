package com.purefunction.ezbt.room.service;

import com.purefunction.ezbt.room.client.agoda.AgodaFeignClient;
import com.purefunction.ezbt.room.client.agoda.dto.AgodaError;
import com.purefunction.ezbt.room.client.agoda.dto.AgodaRequest;
import com.purefunction.ezbt.room.client.agoda.dto.AgodaResponse;
import com.purefunction.ezbt.room.client.agoda.dto.AgodaSuccess;
import com.purefunction.ezbt.room.mapper.RoomMapper;
import com.purefunction.ezbt.room.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value; // Import Value
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final AgodaFeignClient agodaFeignClient;

    @Value("${agoda.api-key}")
    private String agodaApiKey;

    public List<Room> searchRooms(AgodaRequest request) {
        validateAgodaRequest(request);

        AgodaResponse response = agodaFeignClient.searchHotel(agodaApiKey, "gzip", request);

        return switch (response) {
            case AgodaSuccess successResponse -> RoomMapper.toRooms(successResponse);
            case AgodaError errorResponse -> throw new RuntimeException();
        };
    }

    private void validateAgodaRequest(AgodaRequest request) {
    }

    public List<String> searchCities(String cityQuery) {
        return List.of("Seoul", "Busan", "Jeju");
    }
}
