package com.example.case_study_module_4.service.product;


import com.example.case_study_module_4.model.product.VehicleType;

import java.util.List;

public interface IVehicleTypeService extends IGenerateService<VehicleType> {
    List<VehicleType> listVehicleType();
}
