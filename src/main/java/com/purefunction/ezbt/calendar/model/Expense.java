package com.purefunction.ezbt.calendar.model;

import com.purefunction.ezbt.calendar.dto.AddExpenseRequest;
import com.purefunction.ezbt.calendar.dto.UpdateExpenseRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 출장 경비를 나타내는 엔티티입니다.
 */
@Entity
@Table(name = "expenses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder.Default
    private boolean deleted = false;

    public static Expense of(AddExpenseRequest request, BusinessTrip businessTrip) {
        Expense expense = new Expense();
        expense.description = request.getDescription();
        expense.amount = request.getAmount();
        expense.currency = request.getCurrency();
        expense.receiptUrl = request.getReceiptUrl();
        expense.businessTrip = businessTrip;
        expense.deleted = false;
        return expense;
    }

    public void update(UpdateExpenseRequest request) {
        this.description = request.getDescription();
        this.amount = request.getAmount();
        this.currency = request.getCurrency();
        this.receiptUrl = request.getReceiptUrl();
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}