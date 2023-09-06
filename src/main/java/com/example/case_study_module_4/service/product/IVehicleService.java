package com.example.case_study_module_4.service.product;



import com.example.case_study_module_4.model.product.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVehicleService extends IGenerateService<Vehicle> {
    Page<Vehicle> list(Pageable pageable);

    void delete(int vehicleId);

    Vehicle getVehicleById(int i);


    void addVehicle(String vehicleName, int vehicleType, String transmission, String fuel, String description, int rentalPrice);

    List<Vehicle> getVehicleAddById();

    void edit(int id,int status);

    Page<Vehicle> listCustomer(Pageable pageable);

    void editMoney(int vehicleId, int money);

    Iterable<Vehicle> trending();
}
