package com.purefunction.ezbt.room.client.agoda.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class AgodaRequest {

    private final Criteria criteria;

    @Builder
    public AgodaRequest(
            int cityId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            SortBy sortBy,
            Double minimumDailyRate,
            Double maximumDailyRate,
            Integer numberOfAdult,
            Double minimumStarRating,
            Double minimumReviewScore
    ) {

        if (checkInDate.isAfter(checkOutDate)) {
            throw new IllegalArgumentException("체크인 날짜는 체크아웃 날짜 이전이어야 합니다");
        }
        if (checkInDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("체크인 날짜는 오늘 또는 이후여야 합니다");
        }
        if (minimumDailyRate > maximumDailyRate) {
            throw new IllegalArgumentException("최소숙박요금이 최대숙박요금보다 클 수 없습니다");
        }

        this.criteria = new Criteria();
        criteria.cityId = cityId;
        criteria.checkInDate = checkInDate;
        criteria.checkOutDate = checkOutDate;

        criteria.additional = new Additional();
        criteria.additional.language = "ko-kr";
        criteria.additional.currency = "KRW";
        criteria.additional.sortBy = sortBy;
        criteria.additional.minimumStarRating = minimumStarRating;
        criteria.additional.minimumReviewScore = minimumReviewScore;

        criteria.additional.dailyRate = new DailyRate();
        criteria.additional.dailyRate.minimum = minimumDailyRate;
        criteria.additional.dailyRate.maximum = maximumDailyRate;

        criteria.additional.occupancy = new Occupancy();
        criteria.additional.occupancy.numberOfAdult = numberOfAdult;
    }


    @Getter
    @ToString
    private static class Criteria {
        private int cityId;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate checkInDate;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate checkOutDate;

        private Additional additional;
    }

    @Getter
    @ToString
    private static class Additional {
        private String language;
        private String currency;
        private SortBy sortBy;
        private DailyRate dailyRate;
        private Occupancy occupancy;
        private Double minimumStarRating;
        private Double minimumReviewScore;

//        private Integer maxResult;
//        private Boolean discountOnly;

    }

    @Getter
    @ToString
    private static class DailyRate {
        private Double minimum;
        private Double maximum;
    }

    @Getter
    @ToString
    private static class Occupancy {
        private Integer numberOfAdult;
//        private Integer numberOfChildren;
//        private List<Integer> childrenAges;
    }
}
