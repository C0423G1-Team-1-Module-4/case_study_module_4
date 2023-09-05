package com.example.case_study_module_4.controller.product;

import com.example.case_study_module_4.model.product.Image;
import com.example.case_study_module_4.model.product.Vehicle;
import com.example.case_study_module_4.service.imp.product.ImageService;
import com.example.case_study_module_4.service.imp.product.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/vehicle")
public class ApiImageController {
    @Autowired
    private ImageService service;
    @Autowired
    private VehicleService vehicleService;

    @PostMapping ("/vehicle/{img}")
    public ResponseEntity<List<Vehicle>> addImage(@PathVariable String img) {
        List<Vehicle> vehicle = vehicleService.getVehicleAddById();
        service.addImage(img,vehicle.get(0).getId());
        if (vehicle.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }
}
