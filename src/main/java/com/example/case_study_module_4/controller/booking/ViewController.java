package com.example.case_study_module_4.controller.booking;

import com.example.case_study_module_4.dto.booking.SearchVehicle;
import com.example.case_study_module_4.model.product.Vehicle;
import com.example.case_study_module_4.model.product.VehicleType;
import com.example.case_study_module_4.service.product.IVehicleService;
import com.example.case_study_module_4.service.product.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("")
public class ViewController {

    @Autowired
    private IVehicleTypeService vehicleTypeService;

    @Autowired
    private IVehicleService vehicleService;

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("title", "Home");
        SearchVehicle searchVehicle = new SearchVehicle();
        searchVehicle.setStart(String.valueOf(LocalDate.now()));
        searchVehicle.setEnd(String.valueOf(LocalDate.now().plusDays(1)));
        Iterable<VehicleType> vehicleTypeList = vehicleTypeService.findAll();
        Iterable<Vehicle> vehicleList = vehicleService.findAll();
        model.addAttribute("searchVehicle", searchVehicle);
        model.addAttribute("vehicleTypeList", vehicleTypeList);
        model.addAttribute("vehicleList", vehicleList);
        return "shop/index";
    }
}
