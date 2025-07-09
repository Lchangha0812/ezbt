package com.purefunction.ezbt.calendar.model;

import com.purefunction.ezbt.calendar.dto.CreateBusinessTripRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "business_trips")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Builder.Default
    private BusinessTripStatus status = BusinessTripStatus.DRAFT;

    @Builder.Default
    @OneToMany(mappedBy = "businessTrip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripApproval> approvals = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "businessTrip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static BusinessTrip of(CreateBusinessTripRequest request, User user) {
        return BusinessTrip.builder()
                .title(request.getTitle())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .destination(request.getDestination())
                .user(user)
                .build();
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
}
