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
    //--------------------------------------------------- ADMIN ---------------------------------------------------------

//--------------------------------------------------- ADMIN ---------------------------------------------------------
    @GetMapping("/admins/sort")
    public String showProductListSort(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 10;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Vehicle> vehicles = service.listSorte(pageable, 1);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("title", "View Detail");
        return "product/table-basic";
    }
    //--------------------------------------------------- ADMIN ---------------------------------------------------------
    @GetMapping("/admins/delete")
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
    //--------------------------------------------------- ADMIN ---------------------------------------------------------
    @GetMapping("/admins/creat")
    public String creatVehicle(Model model) {
        Vehicle vehicles = new Vehicle();
        List<VehicleType> vehicleTypeList = iVehicleTypeService.listVehicleType();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("vehicleTypeList", vehicleTypeList);
        model.addAttribute("title", "View Detail");
        return "product/ad-listing";
    }
    //--------------------------------------------------- CUSTOMER ---------------------------------------------------------
    @GetMapping("/vehicle/{id}")
    public String List(Model model, @PathVariable int id) {
        SearchVehicle searchVehicle = new SearchVehicle();
        searchVehicle.setStart(String.valueOf(LocalDate.now()));
        searchVehicle.setEnd(String.valueOf(LocalDate.now().plusDays(1)));
        Iterable<VehicleType> vehicleTypeList = vehicleTypeService.findAll();
        model.addAttribute("searchVehicle", searchVehicle);
        model.addAttribute("vehicleTypeList", vehicleTypeList);
        Vehicle vehicle = service.getVehicleById(id);
        model.addAttribute("car", vehicle);
        model.addAttribute("title", "View Detail");
        return "product/single";
    }
    //--------------------------------------------------- CUSTOMER ---------------------------------------------------------
    @PostMapping("/search")
    public String searchProducts(@RequestParam(name = "fuels", required = false, defaultValue = "") List<String> fuels,
                                 @RequestParam(name = "priceRange", required = false) String priceRange,
                                 @RequestParam(name = "transmission", required = false, defaultValue = "") List<String> transmission,
                                 @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                 @RequestParam(name = "name", required = false, defaultValue = "0") int name,
                                 @RequestParam(name = "sort", required = false, defaultValue = "0") int sort,
                                 Model model) {
        int minPrice = 0;
        int maxPrice = 1000;
        String fuelsOne = "Xăng";
        String fuelsTwo = "Dầu";
        String fuelsThree = "Điện";
        String transmissionOne = "Số Tự Động";
        String transmissionTwo = "Số Sàn";
        if (fuels.size() != 0) {
            if (0 >= 0 && 0 < fuels.size()) {
                fuelsOne = fuels.get(0);
            } else {
                fuelsOne = "1";
            }
            if (1 >= 0 && 1 < fuels.size()) {
                fuelsTwo = fuels.get(1);
            } else {
                fuelsTwo = "1";
            }
            if (2 >= 0 && 2 < fuels.size()) {
                fuelsThree = fuels.get(2);
            } else {
                fuelsThree = "1";
            }
        }
        if (transmission.size() != 0) {
            if (0 >= 0 && 0 < transmission.size()) {
                transmissionOne = transmission.get(0);
            } else {
                transmissionOne = "1";
            }
            if (1 >= 0 && 1 < transmission.size()) {
                transmissionTwo = transmission.get(1);
            } else {
                transmissionTwo = "1";
            }
        }
        if (priceRange != null) {
            String[] priceValues = priceRange.split(",");
            if (priceValues.length == 2) {
                minPrice = Integer.parseInt(priceValues[0]);
                maxPrice = Integer.parseInt(priceValues[1]);
            }
        }
        int pageSize = 9;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Vehicle> carsPage = null;
        if (sort == 0) {
            carsPage = service.listCustomerSearch(pageable, name, minPrice, maxPrice, fuelsOne, fuelsTwo, fuelsThree, transmissionOne, transmissionTwo);
        } else if (sort == 1) {
            carsPage = service.sorteSearch(pageable, name, minPrice, maxPrice, fuelsOne, fuelsTwo, fuelsThree, transmissionOne, transmissionTwo);
        } else if (sort == 2) {
            carsPage = service.sorteOneSearch(pageable, name, minPrice, maxPrice, fuelsOne, fuelsTwo, fuelsThree, transmissionOne, transmissionTwo);
        }
        model.addAttribute("carsPage", carsPage);
        model.addAttribute("name", name);
        model.addAttribute("sort", sort);
        model.addAttribute("title", "View Detail");
        return "product/category";
    }

    //--------------------------------------------------- CUSTOMER ---------------------------------------------------------
    @GetMapping("/vehicle/view")
    public String showProduct(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "0") int name,
                              @RequestParam(defaultValue = "0") int sort
    ) {
        int pageSize = 9;
        PageRequest pageable = PageRequest.of(page, pageSize);
        Page<Vehicle> carsPage = null;
        if (sort == 0) {
            carsPage = service.listCustomer(pageable, name);
        } else if (sort == 1) {
            carsPage = service.sorte(pageable, name);
        } else if (sort == 2) {
            carsPage = service.sorteOne(pageable, name);
        }
        model.addAttribute("carsPage", carsPage);
        model.addAttribute("name", name);
        model.addAttribute("sort", sort);
        model.addAttribute("title", "View Detail");
        return "product/category";
    }



}
