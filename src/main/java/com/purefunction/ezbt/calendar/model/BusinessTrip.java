package com.purefunction.ezbt.calendar.model;

import com.purefunction.ezbt.calendar.dto.CreateBusinessTripRequest;
import com.purefunction.ezbt.calendar.dto.UpdateBusinessTripRequest; // Added this import
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 출장 정보를 나타내는 엔티티입니다.
 */
@Entity
@Table(name = "business_trips")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessTripStatus status = BusinessTripStatus.DRAFT;

    

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    private boolean deleted = false;

    public static BusinessTrip of(CreateBusinessTripRequest request, User user) {
        BusinessTrip businessTrip = new BusinessTrip();
        businessTrip.title = request.getTitle();
        businessTrip.startDate = request.getStartDate();
        businessTrip.endDate = request.getEndDate();
        businessTrip.destination = request.getDestination();
        businessTrip.user = user;
        businessTrip.status = BusinessTripStatus.DRAFT;
        businessTrip.deleted = false;
        return businessTrip;
    }

    public void update(UpdateBusinessTripRequest request) {
        this.title = request.getTitle();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.destination = request.getDestination();
    }

    public void requestApproval() {
        if (this.status == BusinessTripStatus.DRAFT) {
            this.status = BusinessTripStatus.PENDING_APPROVAL;
        } else {
            throw new IllegalStateException("Business trip must be in DRAFT status to request approval.");
        }
    }

    public void approve() {
        if (this.status == BusinessTripStatus.PENDING_APPROVAL) {
            this.status = BusinessTripStatus.APPROVED;
        } else {
            throw new IllegalStateException("Business trip must be in PENDING_APPROVAL status to approve.");
        }
    }

    public void reject() {
        if (this.status == BusinessTripStatus.PENDING_APPROVAL) {
            this.status = BusinessTripStatus.REJECTED;
        } else {
            throw new IllegalStateException("Business trip must be in PENDING_APPROVAL status to reject.");
        }
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}