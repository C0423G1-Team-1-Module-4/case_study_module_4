package com.example.case_study_module_4.repository.booking;

import com.example.case_study_module_4.model.booking.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IContractRepository extends JpaRepository<Contract, Integer> {

    @Query(value = "SELECT c.*, e.employee_name, cu.name " +
            "FROM contract c JOIN booking b ON c.booking_id = b.id " +
            "JOIN vehicle v ON b.vehicle_id = v.id " +
            "JOIN customer cu ON b.customer_id = cu.id " +
            "left JOIN employee e ON c.employee_id = e.id " +
            "WHERE v.vehicle_name LIKE :search " +
            "or cu.name LIKE :search " +
            "or e.employee_name LIKE :search " +
            "or c.contract_creation_date like :search " +
            "or c.receive_address like :search " +
            "order by :sort :condition", nativeQuery = true)
    Page<Contract> findContractBySearch(Pageable pageable,  @Param("search") String search,  @Param("sort") String sort, @Param("condition") String condition);
}
