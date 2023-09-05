package com.example.case_study_module_4.service.imp.product;


import com.example.case_study_module_4.model.product.Vehicle;
import com.example.case_study_module_4.repository.product.IVehicleRepository;
import com.example.case_study_module_4.service.product.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService implements IVehicleService {

    @Autowired
    private IVehicleRepository vehicleRepository;

    @Override
    public Iterable<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> findById(Integer id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Override
    public void remove(Integer id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<Vehicle> list() {
        return vehicleRepository.findAllBy();
    }

    @Override
    public void delete(int vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    @Override
    public Vehicle getVehicleById(int i) {
        return vehicleRepository.getReferenceById(i);
    }

    @Override
    public void addVehicle(String vehicleName, int vehicleType, String transmission, String fuel, String description, int rentalPrice) {
        vehicleRepository.add(vehicleName,vehicleType,transmission,fuel,description,rentalPrice);
    }

    @Override
    public List<Vehicle> getVehicleAddById() {
        return vehicleRepository.max();
    }

    @Override
    public void edit(int id, int status) {
        vehicleRepository.edit(id,status);
    }

    @Override
    public List<Vehicle> listCustomer() {
        return vehicleRepository.list();
    }

    @Override
    public void editMoney(int vehicleId, int money) {
        vehicleRepository.editMoney(vehicleId,money);
    }


}
