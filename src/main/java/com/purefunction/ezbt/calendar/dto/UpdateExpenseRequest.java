package com.purefunction.ezbt.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExpenseRequest {
    private String description;
    private BigDecimal amount;
    private String currency;
    private String receiptUrl;
}
