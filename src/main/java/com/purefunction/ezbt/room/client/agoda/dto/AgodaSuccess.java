package com.purefunction.ezbt.room.client.agoda.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AgodaSuccess(List<Result> results) implements AgodaResponse {

    @Override
    public boolean isSuccess() {
        return true;
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record Result(
            Integer hotelId,
            String hotelName,
            String roomtypeName,
            Double starRating,
            Double reviewScore,
            Integer reviewCount,
            String currency,
            Double dailyRate,
            Double crossedOutRate,
            Double discountPercentage,
            String imageURL,
            String landingURL,
            Boolean includeBreakfast,
            Boolean freeWifi
    ) {
    }
}