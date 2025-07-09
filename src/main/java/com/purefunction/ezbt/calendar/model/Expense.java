package com.purefunction.ezbt.calendar.model;

import com.purefunction.ezbt.calendar.dto.AddExpenseRequest;
import com.purefunction.ezbt.calendar.dto.UpdateExpenseRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private BusinessTrip businessTrip;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "receipt_url")
    private String receiptUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static Expense of(AddExpenseRequest request, BusinessTrip businessTrip) {
        return Expense.builder()
                .description(request.getDescription())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .receiptUrl(request.getReceiptUrl())
                .businessTrip(businessTrip)
                .build();
    }

    public void update(UpdateExpenseRequest request) {
        this.description = request.getDescription();
        this.amount = request.getAmount();
        this.currency = request.getCurrency();
        this.receiptUrl = request.getReceiptUrl();
    }
}
