package com.example.case_study_module_4.service.product;



import com.example.case_study_module_4.model.product.Vehicle;

import java.util.List;

public interface IVehicleService extends IGenerateService<Vehicle> {
    List<Vehicle> list();

    void delete(int vehicleId);

    Vehicle getVehicleById(int i);
}