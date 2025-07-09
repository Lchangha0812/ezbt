package com.purefunction.ezbt.calendar.repository;

import com.purefunction.ezbt.calendar.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
