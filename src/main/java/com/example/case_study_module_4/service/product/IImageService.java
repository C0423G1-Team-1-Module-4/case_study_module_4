package com.example.case_study_module_4.service.product;


import com.example.case_study_module_4.model.product.Image;

public interface IImageService extends IGenerateService<Image> {
    Iterable<Image> findAllByVehicleContaining(int vehicleId);

    void addImage(String image, int id);
}
