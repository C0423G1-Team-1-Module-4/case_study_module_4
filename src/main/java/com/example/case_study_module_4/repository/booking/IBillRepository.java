package com.example.case_study_module_4.repository.booking;

import com.example.case_study_module_4.model.booking.Bill;
import com.example.case_study_module_4.model.booking.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IBillRepository extends JpaRepository<Bill, Integer> {

    Bill getBillByContract(Contract contract);

    @Query(value = "SELECT bi.*, e.employee_name, cu.name " +
            "FROM bill bi " +
            "join contract c on bi.contract_id = c.id " +
            "JOIN booking b ON c.booking_id = b.id " +
            "JOIN vehicle v ON b.vehicle_id = v.id " +
            "JOIN customer cu ON b.customer_id = cu.id " +
            "left JOIN employee e ON c.employee_id = e.id " +
            "WHERE (v.vehicle_name LIKE :search " +
            "or cu.name LIKE :search " +
            "or e.employee_name LIKE :search " +
            "or bi.actual_receive_date like :search " +
            "or bi.actual_return_date like :search) " , nativeQuery = true)
    Page<Bill> findBillBySearch(Pageable pageable, @Param("search") String search);
}
