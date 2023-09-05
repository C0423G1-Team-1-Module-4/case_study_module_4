package com.example.case_study_module_4.controller.booking;

import com.example.case_study_module_4.dto.booking.BookingDto;
import com.example.case_study_module_4.dto.booking.ContractDto;
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
        BookingDto bookingDto = new BookingDto();
        bookingDto.setReceiveDate(String.valueOf(LocalDate.now()));
        bookingDto.setReturnDate(String.valueOf(LocalDate.now().plusDays(1)));
        Customer customer = new Customer();
        customer.setName("Thôi óc chó");
        customer.setId(1);
        bookingDto.setCustomer(customer);
        bookingDto.setVehicle(vehicle);
        model.addAttribute("bookingDto", bookingDto);
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
            model.addAttribute("bookingDto", bookingDto);
            return "booking/rent";
        } else {
            Booking booking = new Booking();
            BeanUtils.copyProperties(bookingDto, booking);
            LocalDate start = LocalDate.parse(booking.getReceiveDate());
            LocalDate end = LocalDate.parse(booking.getReturnDate());
            int daysBetween = (int) ChronoUnit.DAYS.between(start, end);
            booking.setRentalPrice(booking.getVehicle().getRentalPrice() * daysBetween);
            bookingService.save(booking);
            ContractDto contractDto = new ContractDto();
            contractDto.setContractCreationDate(String.valueOf(LocalDate.now()));
            contractDto.setRentalFee(daysBetween * booking.getVehicle().getRentalPrice());
            contractDto.setBooking(booking);
            List<CollateralAssets> collateralAssetsList = collateralAssetsService.findAll();
            model.addAttribute("contractDto", contractDto);
            model.addAttribute("daysBetween", daysBetween);
            model.addAttribute("collateralAssetsList", collateralAssetsList);
            return "booking/contract";
        }
    }

    @PostMapping("/create/contract")
    public String createContract(@Validated ContractDto contractDto, BindingResult bindingResult, Model model) {
        new ContractDto().validate(contractDto, bindingResult);
        if (bindingResult.hasErrors()) {
            List<CollateralAssets> collateralAssetsList = collateralAssetsService.findAll();
            model.addAttribute("collateralAssetsList", collateralAssetsList);
            return "booking/contract";
        } else {
            Contract contract = new Contract();
            BeanUtils.copyProperties(contractDto, contract);
            contract.setStatus_confirm(0);
            contractService.save(contract);
            return "redirect:/bookings/show/contract/" + contract.getId();
        }
    }

    @GetMapping("/show/contract/{id}")
    public String showContract(Model model, @PathVariable int id) {
        contractService.findById(id).ifPresent(contract -> model.addAttribute("contractDto", contract));
        return "booking/done";
    }
}
