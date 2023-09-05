package com.example.case_study_module_4.controller.booking;

import com.example.case_study_module_4.model.booking.Contract;
import com.example.case_study_module_4.model.emloyee.Employee;
import com.example.case_study_module_4.service.booking.*;
import com.example.case_study_module_4.service.product.IVehicleService;
import com.example.case_study_module_4.service.product.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Controller
@RequestMapping("/admins")
public class BookingManagerController {

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

    @GetMapping("/booking")
    public String showListBooking(
            Model model,
            @PageableDefault(
                    page = 0,
            sort = "contract_creation_date") Pageable pageable,
            @RequestParam Optional<String> search,
            @RequestParam(required = false) String sortProperty,
            @RequestParam(required = false) String condition) {

        if (sortProperty == null || sortProperty.isEmpty()) {
            sortProperty = "contract_creation_date";
        }
        if (condition == null || condition.isEmpty()) {
            condition = "desc";
        }
        Sort sort = Sort.by(condition.equalsIgnoreCase("asc") ? Sort.Order.asc(sortProperty) : Sort.Order.desc(sortProperty));

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        String valueSearch = "";
        if (search.isPresent()) {
            valueSearch = search.get();
        }
        Page<Contract> contracts = contractService.findAllBySearch(pageable, valueSearch, sortProperty, condition);
        model.addAttribute("bookings", contracts);
        model.addAttribute("search", valueSearch);
        model.addAttribute("sortProperty", sortProperty);
        return "admin/booking/bookings";
    }

    @GetMapping("/confirm/{id}/{status}")
    public String confirmBooking(Model model, @PathVariable int id, @PathVariable int status) {
        Contract contract = contractService.findById(id).orElse(null);
        Employee employee = new Employee();
        employee.setEmployeeName("Hai");
        employee.setId(1);
        if (contract != null) {
            if (status == 1) {
                contract.setEmployee(employee);
                contract.setStatus_confirm(1);
                contractService.save(contract);
            } else {
                contract.setStatus_confirm(2);
                contract.setEmployee(employee);
                contractService.save(contract);
            }
        }
        return "redirect:/admins/booking";
    }
}
