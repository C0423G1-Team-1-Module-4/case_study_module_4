package com.example.case_study_module_4.repository.product;


import com.example.case_study_module_4.model.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IImageRepository extends JpaRepository<Image, Integer> {
    Iterable<Image> findAllByVehicleContaining(int vehicleId);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO image (image_path, vehicle_id) VALUES (:image, :id)",nativeQuery = true)
    void addImage(String image, int id);
}
