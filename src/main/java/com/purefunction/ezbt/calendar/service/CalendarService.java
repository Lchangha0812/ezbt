package com.purefunction.ezbt.calendar.service;

import com.purefunction.ezbt.calendar.dto.*;
import com.purefunction.ezbt.calendar.model.ApprovalStatus;
import com.purefunction.ezbt.calendar.model.BusinessTrip;
import com.purefunction.ezbt.calendar.model.Expense;
import com.purefunction.ezbt.calendar.model.TripApproval;
import com.purefunction.ezbt.calendar.model.User;
import com.purefunction.ezbt.calendar.repository.BusinessTripRepository;
import com.purefunction.ezbt.calendar.repository.ExpenseRepository;
import com.purefunction.ezbt.calendar.repository.TripApprovalRepository;
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
    private final ExpenseRepository expenseRepository;
    private final TripApprovalRepository tripApprovalRepository;

    /**
     * 새로운 출장 일정을 생성합니다.
     *
     * @param request 생성할 출장 정보 (제목, 시작일, 종료일, 목적지, 사용자 이름)
     * @return 생성된 출장 정보 DTO
     * @throws IllegalArgumentException 사용자를 찾을 수 없을 경우 발생
     */
    @Transactional
    public BusinessTripDto createBusinessTrip(CreateBusinessTripRequest request) {
        User user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUserName()));

        BusinessTrip businessTrip = BusinessTrip.of(request, user);
        BusinessTrip savedBusinessTrip = businessTripRepository.save(businessTrip);
        return BusinessTripDto.from(savedBusinessTrip);
    }

    /**
     * 모든 출장 일정을 조회합니다.
     *
     * @return 모든 출장 정보 DTO 목록
     */
    public List<BusinessTripDto> getAllBusinessTrips() {
        return businessTripRepository.findAllByDeletedFalse().stream()
                .map(BusinessTripDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 특정 ID의 출장 일정을 조회합니다.
     *
     * @param tripId 조회할 출장 ID
     * @return 해당 ID의 출장 정보 DTO
     * @throws IllegalArgumentException 해당 ID의 출장을 찾을 수 없을 경우 발생
     */
    public BusinessTripDto getBusinessTripById(Long tripId) {
        BusinessTrip businessTrip = businessTripRepository.findByIdAndDeletedFalse(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        return BusinessTripDto.from(businessTrip);
    }

    /**
     * 다양한 조건으로 출장 일정을 검색합니다.
     *
     * @param startDate   검색 시작일
     * @param endDate     검색 종료일
     * @param destination 목적지 (부분 일치 검색)
     * @param userName    사용자 이름 (부분 일치 검색)
     * @return 검색 조건에 맞는 출장 정보 DTO 목록
     */
    public List<BusinessTripDto> searchBusinessTrips(LocalDate startDate, LocalDate endDate, String destination, String userName) {
        List<BusinessTrip> businessTrips = businessTripRepository.findByCriteria(startDate, endDate, destination, userName);
        return businessTrips.stream()
                .map(BusinessTripDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 기존 출장 일정을 수정합니다.
     *
     * @param tripId  수정할 출장 ID
     * @param request 수정할 출장 정보 (제목, 시작일, 종료일, 목적지)
     * @return 수정된 출장 정보 DTO
     * @throws IllegalArgumentException 해당 ID의 출장을 찾을 수 없을 경우 발생
     */
    @Transactional
    public BusinessTripDto updateBusinessTrip(Long tripId, UpdateBusinessTripRequest request) {
        BusinessTrip businessTrip = businessTripRepository.findByIdAndDeletedFalse(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        businessTrip.update(request);
        return BusinessTripDto.from(businessTrip);
    }

    /**
     * 특정 ID의 출장 일정을 삭제합니다.
     *
     * @param tripId 삭제할 출장 ID
     */
    @Transactional
    public void deleteBusinessTrip(Long tripId) {
        BusinessTrip businessTrip = businessTripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        businessTrip.setDeleted(true);
        businessTripRepository.save(businessTrip);
    }

    /**
     * 외부 캘린더와 동기화합니다. (현재는 구현되지 않음)
     */
    public void syncWithExternalCalendar() {
    }

    /**
     * 출장 결재를 요청합니다.
     * 출장 상태가 'DRAFT'일 경우에만 'PENDING_APPROVAL' 상태로 변경합니다.
     *
     * @param tripId 결재를 요청할 출장 ID
     * @throws IllegalArgumentException 해당 ID의 출장을 찾을 수 없을 경우 발생
     * @throws IllegalStateException    출장 상태가 'DRAFT'가 아닐 경우 발생
     */
    @Transactional
    public void requestApproval(Long tripId) {
        BusinessTrip businessTrip = businessTripRepository.findByIdAndDeletedFalse(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        businessTrip.requestApproval();
    }

    /**
     * 출장 결재를 처리합니다. (승인 또는 거절)
     *
     * @param tripId  결재를 처리할 출장 ID
     * @param request 결재 정보 (승인 여부, 코멘트)
     * @throws IllegalArgumentException 해당 ID의 출장 또는 결재자를 찾을 수 없을 경우 발생
     */
    @Transactional
    public void handleApproval(Long tripId, ApprovalRequest request) {
        BusinessTrip businessTrip = businessTripRepository.findByIdAndDeletedFalse(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));

        User approver = userRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Approver not found"));

        TripApproval approval = TripApproval.builder()
                .businessTrip(businessTrip)
                .approver(approver)
                .comment(request.getComment())
                .status(request.isApproved() ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED)
                .build();

        tripApprovalRepository.save(approval);

        if (request.isApproved()) {
            businessTrip.approve();
        } else {
            businessTrip.reject();
        }
    }

    /**
     * 특정 출장의 현재 결재 상태를 조회합니다.
     *
     * @param tripId 조회할 출장 ID
     * @return 출장의 현재 상태 (e.g., "DRAFT", "APPROVED")
     * @throws IllegalArgumentException 해당 ID의 출장을 찾을 수 없을 경우 발생
     */
    public String getApprovalStatus(Long tripId) {
        BusinessTrip businessTrip = businessTripRepository.findByIdAndDeletedFalse(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        return businessTrip.getStatus().name();
    }

    /**
     * 출장에 경비를 추가합니다.
     *
     * @param tripId  경비를 추가할 출장 ID
     * @param request 추가할 경비 정보 (설명, 금액, 통화, 영수증 URL)
     * @return 추가된 경비 정보 DTO
     * @throws IllegalArgumentException 해당 ID의 출장을 찾을 수 없을 경우 발생
     */
    @Transactional
    public ExpenseDto addExpense(Long tripId, AddExpenseRequest request) {
        BusinessTrip businessTrip = businessTripRepository.findByIdAndDeletedFalse(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        Expense expense = Expense.of(request, businessTrip);
        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseDto.from(savedExpense);
    }

    /**
     * 특정 출장의 모든 경비 내역을 조회합니다.
     *
     * @param tripId 경비 내역을 조회할 출장 ID
     * @return 해당 출장의 모든 경비 정보 DTO 목록
     * @throws IllegalArgumentException 해당 ID의 출장을 찾을 수 없을 경우 발생
     */
    public List<ExpenseDto> getExpenses(Long tripId) {
        BusinessTrip businessTrip = businessTripRepository.findByIdAndDeletedFalse(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Business trip not found: " + tripId));
        return expenseRepository.findByBusinessTripIdAndDeletedFalse(tripId).stream()
                .map(ExpenseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 특정 출장의 특정 경비를 수정합니다.
     *
     * @param tripId    해당 경비가 속한 출장 ID
     * @param expenseId 수정할 경비 ID
     * @param request   수정할 경비 정보
     * @return 수정된 경비 정보 DTO
     * @throws IllegalArgumentException 해당 ID의 출장 또는 경비를 찾을 수 없을 경우 발생
     */
    @Transactional
    public ExpenseDto updateExpense(Long tripId, Long expenseId, UpdateExpenseRequest request) {
        Expense expense = expenseRepository.findByIdAndDeletedFalse(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found: " + expenseId));
        expense.update(request);
        return ExpenseDto.from(expense);
    }

    /**
     * 특정 출장의 특정 경비를 삭제합니다.
     *
     * @param tripId    해당 경비가 속한 출장 ID
     * @param expenseId 삭제할 경비 ID
     * @throws IllegalArgumentException 해당 ID의 출장 또는 경비를 찾을 수 없을 경우 발생
     */
    @Transactional
    public void deleteExpense(Long tripId, Long expenseId) {
        Expense expense = expenseRepository.findByIdAndDeletedFalse(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found: " + expenseId));
        expense.setDeleted(true);
        expenseRepository.save(expense);
    }
}
