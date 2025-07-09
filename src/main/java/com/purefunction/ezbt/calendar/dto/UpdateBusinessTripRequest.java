package com.purefunction.ezbt.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBusinessTripRequest {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destination;
}
