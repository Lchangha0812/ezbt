package com.purefunction.ezbt.calendar.repository;

import com.purefunction.ezbt.calendar.model.BusinessTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BusinessTripRepository extends JpaRepository<BusinessTrip, Long> {

    @Query("SELECT bt FROM BusinessTrip bt JOIN FETCH bt.user u " +
           "WHERE (:startDate IS NULL OR bt.startDate >= :startDate) " +
           "AND (:endDate IS NULL OR bt.endDate <= :endDate) " +
           "AND (:destination IS NULL OR LOWER(bt.destination) LIKE LOWER(CONCAT('%', :destination, '%'))) " +
           "AND (:userName IS NULL OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :userName, '%')))")
    List<BusinessTrip> findByCriteria(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("destination") String destination,
            @Param("userName") String userName
    );
}
