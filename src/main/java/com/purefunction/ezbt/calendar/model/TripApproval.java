package com.purefunction.ezbt.calendar.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 출장 승인 이력을 나타내는 엔티티입니다.
 */
@Entity
@Table(name = "trip_approvals")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private BusinessTrip businessTrip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", nullable = false)
    private User approver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus status;

    @Lob
    private String comment;

    @Builder
    public TripApproval(BusinessTrip businessTrip, User approver, ApprovalStatus status, String comment) {
        this.businessTrip = businessTrip;
        this.approver = approver;
        this.status = status;
        this.comment = comment;
    }

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private boolean deleted = false;

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}