package com.example.case_study_module_4.controller.customer;


import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.account.model.Role;
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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
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
                               Model model ,Principal principal) {
//        if (principal == null){
//            return "redirect:/";
//        }
        Pageable pageable = PageRequest.of(page, 10);
        Page<ICustomerDto> page1 = customerService.findAllCustomer(searchName, pageable);
        model.addAttribute("customerList", page1);
        model.addAttribute("title", "View Detail");
        model.addAttribute("searchName", searchName);
        return "admin/customer/list";
    }
    @GetMapping("/create")
    public String createForm(Model model){
//        Account account = new Account();
//        Role role = new Role();
//        role.setId(2);
//        role.setRoleName("customer");
//        account.setRole(role);
        CustomerDto customerDto = new CustomerDto();
        model.addAttribute("title", "Create Detail");
        model.addAttribute("customer", customerDto);
        return "admin/customer/create-customer";
    }
    @PostMapping("/create")
    public String createCustomer(CustomerDto customerDto, Model model, Principal principal, @RequestParam String image){
        String name = principal.getName();
        Account account = accountService.findByUserName(name);
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        customer.setImageIdCard(image);
        customer.setAccount(account);
        customerService.save(customer);
        model.addAttribute("message", "New Customer Created Successfully!");
        return "redirect:/customers";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model){
        Optional<Customer> customer = customerService.findById(id);
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer.get(),customerDto);
        model.addAttribute("title", "Edit Detail");
        model.addAttribute("customerDto", customerDto);
        return "admin/customer/edit-customer";
    }
    @PostMapping("/edit")
    public String editCustomer(CustomerDto customerDto ,Model model){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customer,customerDto);
        customerService.save(customer);
        model.addAttribute("message", "Edit successfully");
        return "redirect:/customers";
    }
    @PostMapping("/delete")
    public String deleteCustomer(@RequestParam int code){
        customerService.deleteById(code);
        return "redirect:/customers";
    }
}
