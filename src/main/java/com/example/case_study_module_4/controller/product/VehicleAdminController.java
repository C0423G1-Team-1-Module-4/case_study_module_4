package com.example.case_study_module_4.controller.product;
import com.example.case_study_module_4.dto.booking.SearchVehicle;
import com.example.case_study_module_4.model.booking.Contract;
import com.example.case_study_module_4.model.product.Vehicle;
import com.example.case_study_module_4.model.product.VehicleType;
import com.example.case_study_module_4.service.booking.IContractService;
import com.example.case_study_module_4.service.product.IImageService;
import com.example.case_study_module_4.service.product.IVehicleService;
import com.example.case_study_module_4.service.product.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@EnableSpringDataWebSupport
@RequestMapping("admins")
public class VehicleAdminController {
    @Autowired
    private IVehicleService service;

    @Autowired
    IImageService imageService;

    @Autowired
    IVehicleTypeService iVehicleTypeService;

    @Autowired
    private IVehicleTypeService vehicleTypeService;
    @Autowired
    private IContractService contractService;

    @ModelAttribute("alert")
    public List<Contract> alert() {
        List<Contract> contractList = contractService.findContract();
        return contractList;
    }

    @ModelAttribute("vehicleTypeList")
    public Iterable<VehicleType> getVehicleTypeList() {
        return vehicleTypeService.findAll();
    }
    @GetMapping("/vehicle")
    public String showProductList(Model model, @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(name = "searchName", required = false, defaultValue = "") String searchName,
                                  Principal principal) {
        if(principal.getName()==null){
            return "/";
        }
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Vehicle> vehicles = service.listSearch(pageable, searchName);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("title", "View Detail");
        model.addAttribute("searchName", searchName);
        return "product/table-basic";
    }
    @GetMapping("/vehicle/sort")
    public String showProductListSort(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Vehicle> vehicles = service.listSorte(pageable, 1);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("title", "View Detail");
        return "product/table-basic";
    }
    //--------------------------------------------------- ADMIN ---------------------------------------------------------
    @GetMapping("/vehicle/delete")
    public String deleteVehicle(@RequestParam(name = "id") int vehicleId,
                                Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(name = "searchName", required = false, defaultValue = "") String searchName) {
        service.delete(vehicleId);
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Vehicle> vehicles = service.listSearch(pageable, searchName);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("title", "View Detail");
        model.addAttribute("searchName", searchName);
        return "product/table-basic";
    }
    @GetMapping("/vehicle/creat")
    public String creatVehicle(Model model) {
        Vehicle vehicles = new Vehicle();
        List<VehicleType> vehicleTypeList = iVehicleTypeService.listVehicleType();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("vehicleTypeList", vehicleTypeList);
        model.addAttribute("title", "View Detail");
        return "product/ad-listing";
    }
    @GetMapping("/vehicle/edit")
    public String editProduct(@RequestParam(name = "id") int vehicleId,
                              @RequestParam(name = "edit") int edit,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(name = "searchName", required = false, defaultValue = "") String searchName,
                              Model model
    ) {
        if (edit == 0) {
            service.edit(vehicleId, 0);
        } else if (edit == 1) {
            service.edit(vehicleId, 1);
        } else if (edit == 2) {
            service.edit(vehicleId, 2);
        }
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Vehicle> vehicles = service.listSearch(pageable, searchName);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("title", "View Detail");
        model.addAttribute("searchName", searchName);
        return "product/table-basic";
    }
    //--------------------------------------------------- ADMIN ---------------------------------------------------------
    @GetMapping("/vehicle/editMoney")
    public String editProductMoney(@RequestParam(name = "id") int vehicleId,
                                   @RequestParam(name = "money") int money,
                                   @RequestParam(defaultValue = "0") int page, Model model,
                                   @RequestParam(name = "searchName", required = false, defaultValue = "") String searchName) {
        service.editMoney(vehicleId, money);
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Vehicle> vehicles = service.listSearch(pageable, searchName);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("title", "View Detail");
        model.addAttribute("searchName", searchName);
        return "product/table-basic";
    }
    //--------------------------------------------------- ADMIN ---------------------------------------------------------
    @PostMapping("/vehicle/vehicle")
    public String handleVehicleForm(@ModelAttribute Vehicle vehicle,
                                    @RequestParam(name = "image", required = false) String image,
                                    @RequestParam(name = "imageOne", required = false) String imageOne,
                                    @RequestParam(name = "imageTwo", required = false) String imageTwo,
                                    @RequestParam(name = "imageThree", required = false) String imageThree
    ) {
        String vehicleName = vehicle.getVehicleName();
        int vehicleType = vehicle.getVehicleType().getId();
        String transmission = vehicle.getTransmission();
        String fuel = vehicle.getFuel();
        String description = vehicle.getDescription();
        int rentalPrice = vehicle.getRentalPrice();
        service.addVehicle(vehicleName, vehicleType, transmission, fuel, description, rentalPrice);
        List<Vehicle> vehicles = service.getVehicleAddById();
        imageService.addImage(image, vehicles.get(0).getId());
        imageService.addImage(imageOne, vehicles.get(0).getId());
        imageService.addImage(imageTwo, vehicles.get(0).getId());
        imageService.addImage(imageThree, vehicles.get(0).getId());
        return "redirect:/admins/vehicle";
    }
}
