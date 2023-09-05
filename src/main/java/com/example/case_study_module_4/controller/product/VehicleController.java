package com.example.case_study_module_4.controller.product;


import com.example.case_study_module_4.dto.booking.SearchVehicle;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("vehicle")
public class VehicleController {

    @Autowired
    private IVehicleService service;

    @Autowired
    IImageService imageService;
    @Autowired
    IVehicleTypeService iVehicleTypeService;

    @Autowired
    private IVehicleTypeService vehicleTypeService;


    @GetMapping("")
    public String showProductList(Model model) {
        List<Vehicle> vehicles = service.list();
        model.addAttribute("vehicles",vehicles);
        model.addAttribute("title", "View Detail");
        return "product/table-basic";
    }
    @GetMapping("/delete")
    public String deleteVehicle(@RequestParam(name = "id") int vehicleId,Model model) {
        service.delete(vehicleId);
        List<Vehicle> vehicles = service.list();
        model.addAttribute("vehicles",vehicles);
        model.addAttribute("title", "View Detail");
        return "product/table-basic";
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
        SearchVehicle searchVehicle = new SearchVehicle();
        searchVehicle.setStart(String.valueOf(LocalDate.now()));
        searchVehicle.setEnd(String.valueOf(LocalDate.now().plusDays(1)));
        Iterable<VehicleType> vehicleTypeList = vehicleTypeService.findAll();
        model.addAttribute("searchVehicle", searchVehicle);
        model.addAttribute("vehicleTypeList", vehicleTypeList);
        Vehicle vehicle = service.getVehicleById(id);
        model.addAttribute("car",vehicle);
        model.addAttribute("title", "View Detail");
        return "product/single";
    }
    @GetMapping("/vehicle/view")
    public String showProduct(Model model) {
        List<Vehicle> cars = service.listCustomer();
        model.addAttribute("cars",cars);
        model.addAttribute("title", "View Detail");
        return "product/category";
    }
    @GetMapping("/edit")
    public String editProduct(@RequestParam(name = "id") int vehicleId) {
        service.edit(vehicleId,0);
        return "redirect:/vehicle";
    } @GetMapping("/editt")
    public String edittProduct(@RequestParam(name = "id") int vehicleId) {
        service.edit(vehicleId,1);
        return "redirect:/vehicle";
    }
    @GetMapping("/edittt")
    public String editttProduct(@RequestParam(name = "id") int vehicleId) {
        service.edit(vehicleId,2);
        return "redirect:/vehicle";
    }
    @GetMapping("/editMoney")
    public String editProductMoney(@RequestParam(name = "id") int vehicleId,
                                   @RequestParam(name = "money") int money) {
        service.editMoney(vehicleId,money);
        return "redirect:/vehicle";
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
