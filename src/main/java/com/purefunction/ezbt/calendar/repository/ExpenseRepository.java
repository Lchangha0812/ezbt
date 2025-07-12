package com.purefunction.ezbt.calendar.repository;

import com.purefunction.ezbt.calendar.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByBusinessTripIdAndDeletedFalse(Long businessTripId);
    Optional<Expense> findByIdAndDeletedFalse(Long id);
    List<Expense> findAllByDeletedFalse();
}
