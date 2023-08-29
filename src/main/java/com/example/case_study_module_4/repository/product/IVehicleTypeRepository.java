package com.example.case_study_module_4.repository.product;


import com.example.case_study_module_4.model.product.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
}
