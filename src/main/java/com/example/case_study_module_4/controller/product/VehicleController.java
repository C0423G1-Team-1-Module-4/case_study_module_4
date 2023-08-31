package com.example.case_study_module_4.controller.product;


import com.example.case_study_module_4.model.product.Vehicle;
import com.example.case_study_module_4.model.product.VehicleType;
import com.example.case_study_module_4.service.product.IImageService;
import com.example.case_study_module_4.service.product.IVehicleService;
import com.example.case_study_module_4.service.product.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("vehicle")
public class VehicleController {

    @Autowired
    IVehicleService service;
    @Autowired
    IImageService imageService;
    @Autowired
    IVehicleTypeService iVehicleTypeService;

    @GetMapping("")
    public String showProductList(Model model) {
        List<Vehicle> vehicles = service.list();
        model.addAttribute("vehicles",vehicles);
        model.addAttribute("title", "View Detail");
        return "product/dashboard-my-ads";
    }
    @GetMapping("/delete")
    public String deleteVehicle(@RequestParam(name = "id") int vehicleId,Model model) {
        service.delete(vehicleId);
        List<Vehicle> vehicles = service.list();
        model.addAttribute("vehicles",vehicles);
        model.addAttribute("title", "View Detail");
        return "product/dashboard-my-ads";
    }
    @GetMapping("/creat")
    public String creatVehicle(Model model) {
        Vehicle vehicles = new Vehicle();
        List<VehicleType> vehicleTypeList = iVehicleTypeService.listVehicleType();
        model.addAttribute("vehicles",vehicles);
        model.addAttribute("vehicleTypeList",vehicleTypeList);
        model.addAttribute("title", "View Detail");
        return "product/ad-listing";
    }
    @GetMapping("/vehicle/{id}")
    public String List(Model model, @PathVariable int id) {
        Vehicle vehicle = service.getVehicleById(id);
        model.addAttribute("car",vehicle);
        model.addAttribute("title", "View Detail");
        return "product/single";
    }
    @GetMapping("/vehicle/view")
    public String showProduct(Model model) {
        List<Vehicle> cars = service.list();
        model.addAttribute("cars",cars);
        model.addAttribute("title", "View Detail");
        return "product/category";
    }
    @PostMapping("/vehicle")
    public String handleVehicleForm(@ModelAttribute Vehicle vehicle,
                                    @RequestParam(name = "image", required = false) String image,
                                    @RequestParam(name = "imageOne", required = false) String imageOne,
                                    @RequestParam(name = "imageTwo", required = false) String imageTwo,
                                    @RequestParam(name = "imageThree", required = false) String imageThree
    ){
        String vehicleName =vehicle.getVehicleName();
        int vehicleType =vehicle.getVehicleType().getId();
        String transmission =vehicle.getTransmission();
        String fuel=vehicle.getFuel();
        String description = vehicle.getDescription();
        int rentalPrice =vehicle.getRentalPrice();
        service.addVehicle(vehicleName,vehicleType,transmission,fuel,description,rentalPrice);
        List<Vehicle> vehicles=service.getVehicleAddById();
        imageService.addImage(image,vehicles.get(0).getId());
        imageService.addImage(imageOne,vehicles.get(0).getId());
        imageService.addImage(imageTwo,vehicles.get(0).getId());
        imageService.addImage(imageThree,vehicles.get(0).getId());
        return "redirect:/vehicle";
    }

}
