package com.example.case_study_module_4.repository.booking;

import com.example.case_study_module_4.model.booking.Bill;
import com.example.case_study_module_4.model.booking.IncidentalExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IIncidentalExpensesRepository extends JpaRepository<IncidentalExpenses, Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from incidental_expenses where id = :id", nativeQuery = true)
    void deleteIncidentalExpensesById(@Param("id") int id);

    List<IncidentalExpenses> findAllByBill(Bill bill);
}
