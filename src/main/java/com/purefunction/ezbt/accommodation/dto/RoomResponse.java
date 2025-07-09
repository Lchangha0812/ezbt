package com.purefunction.ezbt.accommodation.dto;

import java.math.BigDecimal;

public record RoomResponse(
        Long id,
        String name,
        String roomTypeName,
        double starRating,
        Double reviewScore,
        Integer reviewCount,
        BigDecimal pricePerNight,
        String currency,
        BigDecimal crossedOutRate,
        Double discountPercentage,
        String thumbnailUrl,
        String landingUrl,
        Boolean includeBreakfast,
        Boolean freeWifi
) {
}
