package com.example.case_study_module_4.controller.customer;

import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping("")
    public String listCustomer(@RequestParam(defaultValue = "0", required = false) int page,
                               @RequestParam(defaultValue = "", required = false) String searchName,
                               Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Customer> page1 = customerService.findAllCustomer(searchName, pageable);
        model.addAttribute("customerList", page1);
        model.addAttribute("searchName", searchName);
        return "/admin/customer/list";
    }
}
