package com.example.case_study_module_4.repository.booking;

import com.example.case_study_module_4.model.booking.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBillRepository extends JpaRepository<Bill, Integer> {
}
