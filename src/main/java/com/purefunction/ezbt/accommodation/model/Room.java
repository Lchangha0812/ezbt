package com.purefunction.ezbt.accommodation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Room {
    private Long id;
    private String name;
    private String roomTypeName;
    private double starRating;
    private Double reviewScore;
    private Integer reviewCount;
    private BigDecimal pricePerNight;
    private String currency;
    private BigDecimal crossedOutRate;
    private Double discountPercentage;
    private String thumbnailUrl;
    private String landingUrl;
    private Boolean includeBreakfast;
    private Boolean freeWifi;
}
