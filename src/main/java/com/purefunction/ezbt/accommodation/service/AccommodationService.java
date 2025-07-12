package com.purefunction.ezbt.accommodation.service;

import com.purefunction.ezbt.accommodation.client.agoda.AgodaFeignClient;
import com.purefunction.ezbt.accommodation.client.agoda.dto.AgodaError;
import com.purefunction.ezbt.accommodation.client.agoda.dto.AgodaRequest;
import com.purefunction.ezbt.accommodation.client.agoda.dto.AgodaResponse;
import com.purefunction.ezbt.accommodation.client.agoda.dto.AgodaSuccess;
import com.purefunction.ezbt.accommodation.mapper.RoomMapper;
import com.purefunction.ezbt.accommodation.model.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value; // Import Value
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AgodaFeignClient agodaFeignClient;

    @Value("${agoda.api-key}")
    private String agodaApiKey;

    public List<?> searchByCoordinates(double latitude,
                                       double longitude) {

        return null; // Fixed: Added return value
    }

    public List<?> searchByCity(String cityName) {
        return null; // Fixed: Added return value
    }


}
