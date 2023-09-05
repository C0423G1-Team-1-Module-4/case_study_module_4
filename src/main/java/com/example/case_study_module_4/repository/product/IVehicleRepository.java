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
    @Query(value = "UPDATE vehicle SET status = 3 WHERE id = :vehicleId", nativeQuery = true)
    void deleteById(int vehicleId);

    @Query(value = "SELECT * FROM case_study.vehicle where vehicle.status = 0 OR vehicle.status = 1 OR vehicle.status = 2 ", nativeQuery = true)
    List<Vehicle> findAllBy();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO vehicle (description, fuel, rental_price, status, transmission, vehicle_name, vehicle_type_id) VALUES (:description, :fuel, :rentalPrice,0, :transmission, :vehicleName, :vehicleType)", nativeQuery = true)
    void add(String vehicleName, int vehicleType, String transmission, String fuel, String description, int rentalPrice);
//    ---------------------------------- Trending card -------------------------------------------
    @Query(value = "SELECT * FROM vehicle WHERE id = (SELECT MAX(id) FROM vehicle)",nativeQuery = true)
    List<Vehicle> max();
//    ----------------------------------------------------------------------------------------------------------
    @Modifying
    @Transactional
    @Query(value = "UPDATE vehicle SET status = :status WHERE id = :id", nativeQuery = true)
    void edit(int id, int status);
    @Query(value = "SELECT * FROM case_study.vehicle where vehicle.status = 0 ", nativeQuery = true)
    List<Vehicle> list();
    @Modifying
    @Transactional
    @Query(value = "UPDATE vehicle SET rental_price = :money WHERE id = :vehicleId", nativeQuery = true)
    void editMoney(int vehicleId, int money);
}



