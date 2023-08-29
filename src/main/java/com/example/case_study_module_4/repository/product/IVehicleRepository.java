package com.example.case_study_module_4.repository.product;


import com.example.case_study_module_4.model.product.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IVehicleRepository extends JpaRepository<Vehicle, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE vehicle SET status = 3 WHERE id = :vehicleId",nativeQuery = true)
    void deleteById(int vehicleId);

    @Query(value = "SELECT * FROM case_study.vehicle where vehicle.status = 1 OR vehicle.status = 0;",nativeQuery = true)
    List<Vehicle> findAllBy();
}
