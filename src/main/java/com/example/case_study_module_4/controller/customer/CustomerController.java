package com.example.case_study_module_4.controller.customer;


import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.account.service.IAccountService;
import com.example.case_study_module_4.dto.customer.CustomerDto;
import com.example.case_study_module_4.dto.customer.ICustomerDto;
import com.example.case_study_module_4.model.booking.Contract;
import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.service.booking.IContractService;
import com.example.case_study_module_4.service.customer.ICustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admins/customers")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IContractService contractService;
    @Autowired
    private IAccountService accountService;

    @ModelAttribute("alert")
    public List<Contract> alert() {
        List<Contract> contractList = contractService.findContract();
        return contractList;
    }

    @GetMapping("")
    public String listCustomer(@RequestParam(defaultValue = "0", required = false) int page,
                               @RequestParam(defaultValue = "", required = false) String searchName,
                               @RequestParam(required = false) String sortProperty,
                               @RequestParam(required = false) String condition,
                               Model model, Principal principal) {
        if (sortProperty == null || sortProperty.isEmpty()) {
            sortProperty = "name";
        }
        if (condition == null || condition.isEmpty()) {
            condition = "desc";
        }
        Sort sort = Sort.by(condition.equalsIgnoreCase("asc") ? Sort.Order.asc(sortProperty) : Sort.Order.desc(sortProperty));
        Pageable pageable = PageRequest.of(page, 2);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<ICustomerDto> page1 = customerService.findAllCustomer(searchName, pageable, sortProperty, condition);
        model.addAttribute("customerList", page1);
        model.addAttribute("title", "View Detail");
        model.addAttribute("searchName", searchName);
        return "admin/customer/list-thien";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        CustomerDto customerDto = new CustomerDto();
        model.addAttribute("title", "Create Detail");
        model.addAttribute("customer", customerDto);
        return "admin/customer/create-customer";
    }

    @PostMapping("/create")
    public String createCustomer(@Validated CustomerDto customerDto, Model model, BindingResult bindingResult, Principal principal) {
        String name = principal.getName();
        Account account = accountService.findByUserName(name);
        if (bindingResult.hasErrors()) {
            return "admin/customer/create-customer";
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        customer.setAccount(account);
        customer.setVerification(0); // false
        customerService.save(customer);
        model.addAttribute("message", "New Customer Created Successfully!");
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<Customer> customer = customerService.findById(id);
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer.get(), customerDto);
        model.addAttribute("title", "Edit Detail");
        model.addAttribute("customerDto", customerDto);
        return "admin/customer/edit-customer-thien";
    }

    @PostMapping("/edit")

    public String editCustomer(@Validated CustomerDto customerDto, Model model
            , BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "admin/customer/edit-customer-thien";
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        Account account = accountService.findByUserName(principal.getName());
        customer.setAccount(account);
        customerService.save(customer);
        model.addAttribute("message", "Edit successfully");
        return "redirect:/customers";
    }

    @PostMapping("/delete")
    public String deleteCustomer(@RequestParam int code) {
        customerService.deleteById(code);
        return "redirect:/customers";
    }
}
