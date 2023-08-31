package com.example.case_study_module_4.controller.booking;

import com.example.case_study_module_4.model.booking.Rent;
import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.model.product.Vehicle;
import com.example.case_study_module_4.service.product.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private IVehicleService vehicleService;

    @GetMapping("rent/{id}")
    public String showRent(Model model, @PathVariable int id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("car", vehicle);
        model.addAttribute("title", "Renting");
        Rent rent = new Rent();
        rent.setReceiveDate(String.valueOf(LocalDate.now()));
        rent.setReturnDate(String.valueOf(LocalDate.now()));
        Customer customer = new Customer();
        customer.setName("Thôi óc chó");
        rent.setCustomer(customer);
        model.addAttribute("rent", rent);
        model.addAttribute("customer", customer);
        return "booking/rent";
    }
}
