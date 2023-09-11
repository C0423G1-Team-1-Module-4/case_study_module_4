package com.example.case_study_module_4.controller.booking;

import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.account.service.IAccountService;
import com.example.case_study_module_4.model.booking.Bill;
import com.example.case_study_module_4.model.booking.Contract;

import com.example.case_study_module_4.model.booking.IncidentalExpenses;
import com.example.case_study_module_4.model.employee.Employee;
import com.example.case_study_module_4.model.product.Vehicle;
import com.example.case_study_module_4.service.booking.*;
import com.example.case_study_module_4.service.employee.IEmployeeService;
import com.example.case_study_module_4.service.product.IVehicleService;
import com.example.case_study_module_4.service.product.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private IEmployeeService employeeService;

    @ModelAttribute("alert")
    public List<Contract> alert() {
        List<Contract> contractList = contractService.findContract();
        return contractList;
    }

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
        model.addAttribute("condition", condition);
        model.addAttribute("sortProperty", sortProperty);
        return "admin/booking/bookings";
    }

    @GetMapping("/return")
    public String showListReturn(
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
        Page<Contract> contracts = contractService.findContractBySearchReturn(pageable, valueSearch, sortProperty, condition);
        model.addAttribute("bookings", contracts);
        model.addAttribute("search", valueSearch);
        model.addAttribute("condition", condition);
        model.addAttribute("sortProperty", sortProperty);
        return "admin/booking/return";
    }

    @GetMapping("/confirm/{id}/{status}")
    public String confirmBooking(Model model, @PathVariable int id, @PathVariable int status, Principal principal) {
        Contract contract = contractService.findById(id).orElse(null);
        String username = principal.getName();
        Account account = iAccountService.findByUserName(username);
        Employee employee = employeeService.getEmployeeByAccount(account);
        if (contract != null) {
            if (status == 1) {
                contract.setEmployee(employee);
                contract.setStatus_confirm(1);
                Vehicle vehicle = contract.getBooking().getVehicle();
                vehicle.setStatus(1);
                vehicleService.save(vehicle);
                contractService.save(contract);
            } else {
                contract.setStatus_confirm(2);
                contract.setEmployee(employee);
                contractService.save(contract);
            }
        }
        return "redirect:/admins/booking";
    }

    @GetMapping("/return/{id}")
    public String returnCar(Model model, @PathVariable int id) {
        Contract contract = contractService.findById(id).orElse(null);
        Bill bill = billService.getBillByContract(contract);
        if (bill == null) {
            bill = new Bill();
            bill.setContract(contract);
        }
        bill.setActualReceiveDate(contract.getBooking().getReceiveDate());
        bill.setActualReturnDate(String.valueOf(LocalDate.now()));
        LocalDate returnDate = LocalDate.parse(contract.getBooking().getReturnDate());
        LocalDate actualReturnDate = LocalDate.now();
        List<IncidentalExpenses> incidentalExpensesList = bill.getIncidentalExpensesList();
        IncidentalExpenses incidentalExpenses = new IncidentalExpenses();
        int daysBetween = (int) ChronoUnit.DAYS.between(returnDate, actualReturnDate);
        if (incidentalExpensesList == null) {
            incidentalExpensesList = new ArrayList<>();
        }
        if (returnDate.isBefore(actualReturnDate) && incidentalExpensesList.isEmpty()) {
            incidentalExpenses.setExpenseName("Out of date");
            incidentalExpenses.setPrice(contract.getBooking().getVehicle().getRentalPrice() * daysBetween);
            incidentalExpenses.setDescription(daysBetween + " days expire");
            incidentalExpenses.setBill(bill);
            incidentalExpensesList.add(incidentalExpenses);
        } else if (!incidentalExpensesList.isEmpty()) {
            if (incidentalExpensesList.get(0).getExpenseName().equals("Out of date")) {
                incidentalExpensesList.get(0).setPrice(contract.getBooking().getVehicle().getRentalPrice() * daysBetween);
                incidentalExpensesList.get(0).setDescription(daysBetween + " days expire");
            }
        }
        bill.setIncidentalExpensesList(incidentalExpensesList);
        billService.save(bill);
        LocalDate start = LocalDate.parse(contract.getBooking().getReceiveDate());
        LocalDate end = LocalDate.parse(contract.getBooking().getReturnDate());
        int days = (int) ChronoUnit.DAYS.between(start, end);
        model.addAttribute("days", days);
        model.addAttribute("bill", bill);
        model.addAttribute("incidentalExpenses", new IncidentalExpenses());
        return "admin/booking/contract_detail";
    }

    @PostMapping("/saveBill")
    public String saveBill(Bill bill) {
        List<IncidentalExpenses> list = incidentalExpensesService.findAllByBill(bill);
        int price = 0;
        for (IncidentalExpenses incidentalExpenses : list) {
            price += incidentalExpenses.getPrice();
        }
        price += bill.getContract().getRentalFee();
        bill.setTotalAmount(price);
        bill.setIncidentalExpensesList(list);
        billService.save(bill);
        return "redirect:/admins/bills";
    }

    @PostMapping("/payment/{id}")
    public String getPay(@RequestParam int payment, @PathVariable int id) {
        Bill bill = billService.findById(id).orElse(null);
        if (payment == 0) {
            bill.getContract().setStatus_confirm(1);
        } else {
            bill.getContract().setStatus_confirm(5);
            Vehicle vehicle = bill.getContract().getBooking().getVehicle();
            vehicle.setStatus(0);
            vehicleService.save(vehicle);
        }
        contractService.save(bill.getContract());
        bill.setPaymentStatus(payment);
        billService.save(bill);
        return "redirect:/admins/bills";
    }

    @GetMapping("/bills")
    public String showListBill(
            Model model,
            @PageableDefault(
                    page = 0,
                    sort = "id") Pageable pageable,
            @RequestParam Optional<String> search,
            @RequestParam(required = false) String sortProperty,
            @RequestParam(required = false) String condition) {

        if (sortProperty == null || sortProperty.isEmpty()) {
            sortProperty = "id";
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
        Page<Bill> bills = billService.findBillBySearch(pageable, valueSearch);
        model.addAttribute("bills", bills);
        model.addAttribute("search", valueSearch);
        model.addAttribute("sortProperty", sortProperty);
        model.addAttribute("condition", condition);
        return "admin/booking/bill";
    }


}
