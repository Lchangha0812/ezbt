package com.purefunction.ezbt.calendar.dto;

import com.purefunction.ezbt.calendar.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private Long id;
    private String description;
    private BigDecimal amount;
    private String currency;
    private String receiptUrl;

    public static ExpenseDto from(Expense expense) {
        return ExpenseDto.builder()
                .id(expense.getId())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .currency(expense.getCurrency())
                .receiptUrl(expense.getReceiptUrl())
                .build();
    }
}
