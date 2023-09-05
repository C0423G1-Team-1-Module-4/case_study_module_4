package com.example.case_study_module_4.controller.customer;


import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.account.model.Role;
import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        return "admin/customer/list";
    }
    @GetMapping("/create")
    public String createForm(Model model){
        Account account = new Account();
        Role role = new Role();
        role.setId(2);
        role.setRoleName("customer");
        account.setRole(role);
        Customer customer = new Customer();
        customer.setAccount(account);
        model.addAttribute("customer", customer);
        return "admin/customer/create-customer";
    }
    @PostMapping("/create")
    public String createCustomer(Customer customer, Model model){
        customerService.save(customer);
//        model.addAttribute("message", "New Customer Created Successfully!");
        return "redirect:/customers";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model){
        Optional<Customer> customer = customerService.findById(id);
        model.addAttribute("customer", new Customer());
        return "/admin/customer/edit-customer";
    }
    @PostMapping("/edit")
    public String editForm(@PathVariable int id, Model model){
        Optional<Customer> customer = customerService.findById(id);
        model.addAttribute("customer", new Customer());
        return "/admin/customer/edit-customer";
    }
}
