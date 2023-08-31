package com.example.case_study_module_4.service.imp.product;


import com.example.case_study_module_4.model.product.VehicleType;
import com.example.case_study_module_4.repository.product.IVehicleTypeRepository;
import com.example.case_study_module_4.service.product.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleTypeService implements IVehicleTypeService {

    @Autowired
    private IVehicleTypeRepository vehicleTypeRepository;

    @Override
    public Iterable<VehicleType> findAll() {
        return vehicleTypeRepository.findAll();
    }

    @Override
    public Optional<VehicleType> findById(Integer id) {
        return vehicleTypeRepository.findById(id);
    }

    @Override
    public void save(VehicleType vehicleType) {
        vehicleTypeRepository.save(vehicleType);

    }
    @Override
    public void remove(Integer id) {
        vehicleTypeRepository.deleteById(id);
    }

    @Override
    public List<VehicleType> listVehicleType() {
        return vehicleTypeRepository.findAll();
    }
}
