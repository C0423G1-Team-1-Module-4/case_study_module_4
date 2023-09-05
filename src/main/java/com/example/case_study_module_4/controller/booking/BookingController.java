package com.example.case_study_module_4.controller.booking;

import com.example.case_study_module_4.dto.booking.RentDto;
import com.example.case_study_module_4.dto.booking.SearchVehicle;
import com.example.case_study_module_4.model.booking.CollateralAssets;
import com.example.case_study_module_4.model.booking.Contract;
import com.example.case_study_module_4.model.booking.Rent;
import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.model.product.Vehicle;
import com.example.case_study_module_4.model.product.VehicleType;
import com.example.case_study_module_4.service.booking.*;
import com.example.case_study_module_4.service.product.IVehicleService;
import com.example.case_study_module_4.service.product.IVehicleTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private IRentService rentService;

    @Autowired
    private IContractService contractService;

    @Autowired
    private ICollateralAssetsService collateralAssetsService;

    @Autowired
    private IIncidentalExpensesService incidentalExpensesService;

    @Autowired
    private IBillService billService;

    @Autowired
    private IVehicleTypeService vehicleTypeService;

    @GetMapping("/rent/{id}")
    public String showRent(Model model, @PathVariable int id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("car", vehicle);
        model.addAttribute("title", "Renting");
        RentDto rentDto = new RentDto();
        rentDto.setReceiveDate(String.valueOf(LocalDate.now()));
        rentDto.setReturnDate(String.valueOf(LocalDate.now().plusDays(1)));
        Customer customer = new Customer();
        customer.setName("Thôi óc chó");
        rentDto.setCustomer(customer);
        rentDto.setVehicle(vehicle);
        model.addAttribute("rentDto", rentDto);
        model.addAttribute("customer", customer);
        return "booking/rent";
    }

    @PostMapping("/rent/{id}")
    public String showContract(@Validated RentDto rentDto, BindingResult bindingResult, Model model, @PathVariable int id) {
        new RentDto().validate(rentDto, bindingResult);
        if (bindingResult.hasErrors()) {
            Vehicle vehicle = vehicleService.getVehicleById(id);
            model.addAttribute("car", vehicle);
            model.addAttribute("title", "Renting");
            Customer customer = new Customer();
            customer.setName("Thôi óc chó");
            rentDto.setCustomer(customer);
            model.addAttribute("customer", customer);
            return "booking/rent";
        } else {
            Rent rent = new Rent();
            BeanUtils.copyProperties(rentDto, rent);
            Contract contract = new Contract();
            LocalDate start = LocalDate.parse(rent.getReceiveDate());
            LocalDate end = LocalDate.parse(rent.getReturnDate());
            contract.setContractCreationDate(String.valueOf(LocalDate.now()));
            int daysBetween = (int) ChronoUnit.DAYS.between(start, end);
            contract.setRentalFee(daysBetween * rent.getVehicle().getRentalPrice());
            contract.setRent(rent);
            List<CollateralAssets> collateralAssetsList = collateralAssetsService.findAll();
            model.addAttribute("contract", contract);
            model.addAttribute("collateralAssetsList", collateralAssetsList);
            return "booking/contract";
        }
    }
}
