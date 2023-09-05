package com.example.case_study_module_4.controller.booking;

import com.example.case_study_module_4.dto.booking.BookingDto;
import com.example.case_study_module_4.model.booking.Booking;
import com.example.case_study_module_4.model.booking.CollateralAssets;
import com.example.case_study_module_4.model.booking.Contract;
import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.model.product.Vehicle;
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
    private IBookingService bookingService;

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

    @GetMapping("/booking/{id}")
    public String showRent(Model model, @PathVariable int id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("car", vehicle);
        model.addAttribute("title", "Renting");
        BookingDto rentDto = new BookingDto();
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

    @PostMapping("/booking/{id}")
    public String showContract(@Validated BookingDto bookingDto, BindingResult bindingResult, Model model, @PathVariable int id) {
        new BookingDto().validate(bookingDto, bindingResult);
        if (bindingResult.hasErrors()) {
            Vehicle vehicle = vehicleService.getVehicleById(id);
            model.addAttribute("car", vehicle);
            model.addAttribute("title", "Renting");
            Customer customer = new Customer();
            customer.setName("Thôi óc chó");
            bookingDto.setCustomer(customer);
            model.addAttribute("customer", customer);
            return "booking/rent";
        } else {
            Booking booking = new Booking();
            BeanUtils.copyProperties(bookingDto, booking);
            LocalDate start = LocalDate.parse(booking.getReceiveDate());
            LocalDate end = LocalDate.parse(booking.getReturnDate());
            int daysBetween = (int) ChronoUnit.DAYS.between(start, end);
            Customer customer = new Customer();
            customer.setName("Thôi óc chó");
            customer.setId(1);
            booking.setCustomer(customer);
            booking.setRentalPrice(booking.getVehicle().getRentalPrice() * daysBetween);
            bookingService.save(booking);
            Contract contract = new Contract();
            contract.setContractCreationDate(String.valueOf(LocalDate.now()));
            contract.setRentalFee(daysBetween * booking.getVehicle().getRentalPrice());
            contract.setBooking(booking);
            List<CollateralAssets> collateralAssetsList = collateralAssetsService.findAll();
            model.addAttribute("contract", contract);
            model.addAttribute("daysBetween", daysBetween);
            model.addAttribute("collateralAssetsList", collateralAssetsList);
            return "booking/contract";
        }
    }

    @PostMapping("/create/contract")
    public String createContract(Model model, Contract contract) {
        contract.setStatus_confirm(0);
        contractService.save(contract);
        return "redirect:/";
    }

    @GetMapping("admins/booking")
    public String showListBooking(Model model) {
        model.addAttribute("bookings", contractService.findAll());
        return "admin/booking/bookings";
    }
}
