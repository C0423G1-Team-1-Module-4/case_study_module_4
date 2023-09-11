package com.example.case_study_module_4.controller.customer;
import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.account.service.IAccountService;
import com.example.case_study_module_4.dto.customer.CustomerDto;
import com.example.case_study_module_4.model.booking.Contract;
import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.service.booking.IContractService;
import com.example.case_study_module_4.service.customer.ICustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/users")
public class UserCustomerController {
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
    @GetMapping("/view-detail")
    public String viewForm(Model model, Principal principal) {
        Account account = accountService.findByUserName(principal.getName());
        Customer customer = customerService.findCustomerByAccount_Id(account.getId());
        CustomerDto customerDto  = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        model.addAttribute("title", "View Detail");
        model.addAttribute("customer", customer);
        return "admin/customer-user/view-detail";
    }
    @PostMapping("/edit")
    public String editCustomer( CustomerDto customerDto, Model model,Principal principal) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        Account account = accountService.findByUserName(principal.getName());
        customer.setAccount(account);
        customerService.save(customer);
        return "redirect:/users/view-detail";
    }
}