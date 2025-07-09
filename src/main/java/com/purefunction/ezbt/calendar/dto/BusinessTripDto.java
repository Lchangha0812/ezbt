package com.purefunction.ezbt.calendar.dto;

import com.purefunction.ezbt.calendar.model.BusinessTrip;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTripDto {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String destination;
    private String userName;

    public static BusinessTripDto from(BusinessTrip businessTrip) {
        return BusinessTripDto.builder()
                .id(businessTrip.getId())
                .title(businessTrip.getTitle())
                .startDate(businessTrip.getStartDate())
                .endDate(businessTrip.getEndDate())
                .destination(businessTrip.getDestination())
                .userName(businessTrip.getUser().getUserName())
                .build();
    }
}
