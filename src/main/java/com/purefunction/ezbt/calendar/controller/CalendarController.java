package com.purefunction.ezbt.calendar.controller;

import com.purefunction.ezbt.calendar.dto.*;
import com.purefunction.ezbt.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    // C
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessTripDto createBusinessTrip(@RequestBody CreateBusinessTripRequest request) {
        return calendarService.createBusinessTrip(request);
    }

    // R
    @GetMapping
    public List<BusinessTripDto> getAllBusinessTrips() {
        return calendarService.getAllBusinessTrips();
    }

    @GetMapping("/{tripId}")
    public BusinessTripDto getBusinessTripById(@PathVariable Long tripId) {
        return calendarService.getBusinessTripById(tripId);
    }

    @GetMapping("/search")
    public List<BusinessTripDto> searchBusinessTrips(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String userName
    ) {
        return calendarService.searchBusinessTrips(startDate, endDate, destination, userName);
    }

    // U
    @PutMapping("/{tripId}")
    public BusinessTripDto updateBusinessTrip(@PathVariable Long tripId, @RequestBody UpdateBusinessTripRequest request) {
        return calendarService.updateBusinessTrip(tripId, request);
    }

    // D
    @DeleteMapping("/{tripId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBusinessTrip(@PathVariable Long tripId) {
        calendarService.deleteBusinessTrip(tripId);
    }

    // External Calendar Integration
    @PostMapping("/sync")
    public void syncWithExternalCalendar() {
        calendarService.syncWithExternalCalendar();
    }

    // Approval Workflow
    @PostMapping("/{tripId}/approval/request")
    public void requestApproval(@PathVariable Long tripId) {
        calendarService.requestApproval(tripId);
    }

    @PostMapping("/{tripId}/approval/handle")
    public void handleApproval(@PathVariable Long tripId, @RequestBody ApprovalRequest request) {
        calendarService.handleApproval(tripId, request);
    }

    @GetMapping("/{tripId}/approval/status")
    public String getApprovalStatus(@PathVariable Long tripId) {
        return calendarService.getApprovalStatus(tripId);
    }

    // Expense/Receipt Handling
    @PostMapping("/{tripId}/expenses")
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseDto addExpense(@PathVariable Long tripId, @RequestBody AddExpenseRequest request) {
        return calendarService.addExpense(tripId, request);
    }

    @GetMapping("/{tripId}/expenses")
    public List<ExpenseDto> getExpenses(@PathVariable Long tripId) {
        return calendarService.getExpenses(tripId);
    }

    @PutMapping("/{tripId}/expenses/{expenseId}")
    public ExpenseDto updateExpense(@PathVariable Long tripId, @PathVariable Long expenseId, @RequestBody UpdateExpenseRequest request) {
        return calendarService.updateExpense(tripId, expenseId, request);
    }

    @DeleteMapping("/{tripId}/expenses/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long tripId, @PathVariable Long expenseId) {
        calendarService.deleteExpense(tripId, expenseId);
    }
}

