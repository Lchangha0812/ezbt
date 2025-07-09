package com.purefunction.ezbt.accommodation.mapper;

import com.purefunction.ezbt.accommodation.client.agoda.dto.AgodaSuccess;
import com.purefunction.ezbt.accommodation.dto.RoomResponse; // RoomResponse import
import com.purefunction.ezbt.accommodation.model.Room;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public static List<Room> toRooms(AgodaSuccess agodaSuccess) {
        if (agodaSuccess == null || agodaSuccess.results() == null) {
            return Collections.emptyList();
        }

        return agodaSuccess.results().stream()
                .map(RoomMapper::toRoom)
                .collect(Collectors.toList());
    }

    private static Room toRoom(AgodaSuccess.Result result) {
        return new Room(
                result.hotelId().longValue(),
                result.hotelName(),
                result.roomtypeName(),
                result.starRating(),
                result.reviewScore(),
                result.reviewCount(),
                BigDecimal.valueOf(result.dailyRate()),
                result.currency(),
                result.crossedOutRate() != null ? BigDecimal.valueOf(result.crossedOutRate()) : null,
                result.discountPercentage(),
                result.imageURL(),
                result.landingURL(),
                result.includeBreakfast(),
                result.freeWifi()
        );
    }

    // Room -> RoomResponse 매핑 메소드 추가
    public static RoomResponse toRoomResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getRoomTypeName(),
                room.getStarRating(),
                room.getReviewScore(),
                room.getReviewCount(),
                room.getPricePerNight(),
                room.getCurrency(),
                room.getCrossedOutRate(),
                room.getDiscountPercentage(),
                room.getThumbnailUrl(),
                room.getLandingUrl(),
                room.getIncludeBreakfast(),
                room.getFreeWifi()
        );
    }

    // List<Room> -> List<RoomResponse> 매핑 메소드 추가
    public static List<RoomResponse> toRoomResponses(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return Collections.emptyList();
        }
        return rooms.stream()
                .map(RoomMapper::toRoomResponse)
                .collect(Collectors.toList());
    }
}
