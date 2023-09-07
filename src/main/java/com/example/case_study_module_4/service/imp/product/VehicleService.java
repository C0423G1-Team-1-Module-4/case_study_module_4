package com.example.case_study_module_4.service.imp.product;


import com.example.case_study_module_4.model.product.Vehicle;
import com.example.case_study_module_4.repository.product.IVehicleRepository;
import com.example.case_study_module_4.service.product.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Vehicle> list(Pageable pageable) {
        return vehicleRepository.findAllBy(pageable);
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
        vehicleRepository.add(vehicleName, vehicleType, transmission, fuel, description, rentalPrice);
    }

    @Override
    public List<Vehicle> getVehicleAddById() {
        return vehicleRepository.max();
    }

    @Override
    public void edit(int id, int status) {
        vehicleRepository.edit(id, status);
    }

    @Override
    public Page<Vehicle> listCustomer(Pageable pageable, int name) {
        if (name == 0) {
            return vehicleRepository.listAll(pageable);
        }
        return vehicleRepository.list(pageable, name);
    }

    @Override
    public void editMoney(int vehicleId, int money) {
        vehicleRepository.editMoney(vehicleId, money);
    }

    @Override
    public Iterable<Vehicle> trending() {
        return vehicleRepository.trending();
    }

    @Override
    public Page<Vehicle> listSorte(PageRequest pageable, int sort) {
        return null;
    }

    @Override
    public Page<Vehicle> listCustomerr(PageRequest pageable) {
        return vehicleRepository.listAll(pageable);
    }

    @Override
    public Page<Vehicle> sorte(PageRequest pageable, int name) {
        if (name == 0) {
            return vehicleRepository.sorteAll(pageable);
        }
        return vehicleRepository.sorte(pageable, name);
    }

    @Override
    public Page<Vehicle> sorteOne(PageRequest pageable, int name) {
        if (name == 0) {
            return vehicleRepository.sorteOneAll(pageable);
        }
        return vehicleRepository.sorteOne(pageable, name);
    }

    @Override
    public Page<Vehicle> listCustomerSearch(PageRequest pageable, int name, int minPrice, int maxPrice, String fuelsOne, String fuelsTwo, String fuelsThree, String transmissionOne, String transmissionTwo) {
        if (name == 0) {
            return vehicleRepository.listAllSearch(pageable,minPrice, maxPrice, fuelsOne, fuelsTwo, fuelsThree, transmissionOne, transmissionTwo);
        }
        return vehicleRepository.listSearch(pageable,name,minPrice, maxPrice, fuelsOne, fuelsTwo, fuelsThree, transmissionOne, transmissionTwo);
    }

    @Override
    public Page<Vehicle> sorteSearch(PageRequest pageable, int name, int minPrice, int maxPrice, String fuelsOne, String fuelsTwo, String fuelsThree, String transmissionOne, String transmissionTwo) {
        if (name == 0) {
            return vehicleRepository.sorteAllSearch(pageable,minPrice, maxPrice, fuelsOne, fuelsTwo, fuelsThree, transmissionOne, transmissionTwo);
        }
        return vehicleRepository.sorteSearch(pageable, name,minPrice, maxPrice, fuelsOne, fuelsTwo, fuelsThree, transmissionOne, transmissionTwo);
    }

    @Override
    public Page<Vehicle> sorteOneSearch(PageRequest pageable, int name, int minPrice, int maxPrice, String fuelsOne, String fuelsTwo, String fuelsThree, String transmissionOne, String transmissionTwo) {
        if (name == 0) {
            return vehicleRepository.sorteOneAllSearch(pageable,minPrice, maxPrice, fuelsOne, fuelsTwo, fuelsThree, transmissionOne, transmissionTwo);
        }
        return vehicleRepository.sorteOneSearch(pageable, name,minPrice, maxPrice, fuelsOne, fuelsTwo, fuelsThree, transmissionOne, transmissionTwo);
    }


}
