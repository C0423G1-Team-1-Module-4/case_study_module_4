package com.example.case_study_module_4.controller.product;


import com.example.case_study_module_4.model.product.Vehicle;
import com.example.case_study_module_4.service.product.IImageService;
import com.example.case_study_module_4.service.product.IVehicleService;
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

    @GetMapping("")
    public String showProductList(Model model) {
        List<Vehicle> vehicles = service.list();
        model.addAttribute("vehicles",vehicles);
        model.addAttribute("title", "View Detail");
        return "product/dashboard-my-ads";
    }
    @GetMapping("/delete")
    public String deleteVehicle(@RequestParam(name = "id") int vehicleId,Model model) {
        List<Vehicle> vehicles = service.list();
        model.addAttribute("vehicles",vehicles);
        model.addAttribute("title", "View Detail");
        service.delete(vehicleId);
        return "product/dashboard-my-ads"; // Chẳng hạn, chuyển hướng người dùng đến trang danh sách phương tiện sau khi xóa.
    }
    @GetMapping("/creat")
    public String creatVehicle(Model model) {
        Vehicle vehicles = new Vehicle();
        model.addAttribute("vehicles",vehicles);
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
    @PostMapping("/vehicle/vehicle")
    public String handleVehicleForm(@ModelAttribute Vehicle vehicle,
                                    @RequestParam("images") List<MultipartFile> images) throws IOException {

        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            if (image != null && image.getContentType() != null && image.getContentType().startsWith("image/")) {
                imageUrls.add(String.valueOf(image));
            }
        }
        for (int i = 0; i < imageUrls.size(); i++) {
            imageService.addImage(imageUrls.get(i),vehicle.getId());
        }
        return "redirect:/vehicle";
    }

}
