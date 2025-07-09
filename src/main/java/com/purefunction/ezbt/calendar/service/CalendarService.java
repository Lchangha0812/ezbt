package com.purefunction.ezbt.calendar.service;

import com.purefunction.ezbt.calendar.dto.*;
import com.purefunction.ezbt.calendar.model.BusinessTrip;
import com.purefunction.ezbt.calendar.model.Expense;
import com.purefunction.ezbt.calendar.model.TripApproval;
import com.purefunction.ezbt.calendar.model.User;
import com.purefunction.ezbt.calendar.repository.BusinessTripRepository;
import com.purefunction.ezbt.calendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarService {

    private final UserRepository userRepository;
    private final BusinessTripRepository businessTripRepository;

    // C
    @Transactional
    public BusinessTripDto createBusinessTrip(CreateBusinessTripRequest request) {
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUserName()));

        BusinessTrip businessTrip = BusinessTrip.of(request, user);
        BusinessTrip savedBusinessTrip = businessTripRepository.save(businessTrip);
        return BusinessTripDto.from(savedBusinessTrip);
    }

    // R
    public List<BusinessTripDto> getAllBusinessTrips() {
        return businessTripRepository.findAll().stream()
                .map(BusinessTripDto::from)
                .collect(Collectors.toList());
    }

    public BusinessTripDto getBusinessTripById(Long tripId) {
        BusinessTrip businessTrip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        return BusinessTripDto.from(businessTrip);
    }

    public List<BusinessTripDto> searchBusinessTrips(LocalDate startDate, LocalDate endDate, String destination, String userName) {
        List<BusinessTrip> businessTrips = businessTripRepository.findByCriteria(startDate, endDate, destination, userName);
        return businessTrips.stream()
                .map(BusinessTripDto::from)
                .collect(Collectors.toList());
    }

    // U
    @Transactional
    public BusinessTripDto updateBusinessTrip(Long tripId, UpdateBusinessTripRequest request) {
        BusinessTrip businessTrip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        businessTrip.update(request);
        // No need to call save explicitly in a transactional context for managed entities
        return BusinessTripDto.from(businessTrip);
    }

    // D
    @Transactional
    public void deleteBusinessTrip(Long tripId) {
        businessTripRepository.deleteById(tripId);
    }

    // External Calendar Integration
    public void syncWithExternalCalendar() {
        // TODO: Implement
    }

    // Approval Workflow
    @Transactional
    public void requestApproval(Long tripId) {
        BusinessTrip businessTrip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        businessTrip.requestApproval();
    }

    @Transactional
    public void handleApproval(Long tripId, ApprovalRequest request) {
        BusinessTrip businessTrip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));

        // Assuming a fixed approver for now, or get from security context
        User approver = userRepository.findById(1L) // Placeholder: Replace with actual approver logic
                .orElseThrow(() -> new IllegalArgumentException("Approver not found"));

        TripApproval approval = TripApproval.builder()
                .businessTrip(businessTrip)
                .approver(approver)
                .comment(request.getComment())
                .status(request.isApproved() ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED)
                .build();

        businessTrip.getApprovals().add(approval); // Add to collection to persist via cascade

        if (request.isApproved()) {
            businessTrip.approve();
        } else {
            businessTrip.reject();
        }
    }

    public String getApprovalStatus(Long tripId) {
        BusinessTrip businessTrip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        return businessTrip.getStatus().name();
    }

    // Expense/Receipt Handling
    @Transactional
    public ExpenseDto addExpense(Long tripId, AddExpenseRequest request) {
        BusinessTrip businessTrip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        Expense expense = Expense.of(request, businessTrip);
        businessTrip.getExpenses().add(expense); // Add to collection to persist via cascade
        return ExpenseDto.from(expense);
    }

    public List<ExpenseDto> getExpenses(Long tripId) {
        BusinessTrip businessTrip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        return businessTrip.getExpenses().stream()
                .map(ExpenseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExpenseDto updateExpense(Long tripId, Long expenseId, UpdateExpenseRequest request) {
        BusinessTrip businessTrip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        Expense expense = businessTrip.getExpenses().stream()
                .filter(e -> e.getId().equals(expenseId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Expense not found: " + expenseId + " for trip: " + tripId));
        expense.update(request);
        return ExpenseDto.from(expense);
    }

    @Transactional
    public void deleteExpense(Long tripId, Long expenseId) {
        BusinessTrip businessTrip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        Expense expense = businessTrip.getExpenses().stream()
                .filter(e -> e.getId().equals(expenseId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Expense not found: " + expenseId + " for trip: " + tripId));
        businessTrip.getExpenses().remove(expense);
        // Due to orphanRemoval = true, this will delete the expense from the database
    }
}
