package com.example.case_study_module_4.controller.employee;

import com.example.case_study_module_4.account.dto.AccountDto;
import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.account.model.Role;
import com.example.case_study_module_4.account.service.IAccountService;
import com.example.case_study_module_4.dto.employee.EmployeeDto;
import com.example.case_study_module_4.dto.employee.IEmployeeDto;
import com.example.case_study_module_4.model.booking.Contract;
import com.example.case_study_module_4.model.employee.Employee;

import com.example.case_study_module_4.service.booking.IContractService;
import com.example.case_study_module_4.service.employee.IEmployeeService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;


@Controller
@RequestMapping("/admins/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
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
    public String showList(@RequestParam(defaultValue = "0", required = false) int page,
                           @RequestParam(defaultValue = "", required = false) String searchName,
                           @RequestParam(required = false) String sortProperty,
                           @RequestParam(required = false) String condition,
                           Model model) {
        if (sortProperty == null || sortProperty.isEmpty()) {
            sortProperty = "acc.status";
        }
        if (condition == null || condition.isEmpty()) {
            condition = "asc";
        }
        Sort sort = Sort.by(condition.equalsIgnoreCase("asc") ? Sort.Order.asc(sortProperty) : Sort.Order.desc(sortProperty));
        Pageable pageable = PageRequest.of(page, 10);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);


        Page<IEmployeeDto> employeeDtos = employeeService.findAll(pageable, searchName, sortProperty, condition);
        model.addAttribute("title", "View Detail");
        model.addAttribute("searchName", searchName);
        model.addAttribute("employeeDtos", employeeDtos);
        return "admin/employee/list-employee-2";
    }

    @GetMapping("/createAccount")
    public String showCreateAccount(Model model) {
        AccountDto accountDto = new AccountDto();
        model.addAttribute("accountDto", accountDto);
        model.addAttribute("title", "Create Account");
        return "admin/employee/create-account";
    }

    @PostMapping("/createAccount")
    public String createAccount(@Validated @ModelAttribute AccountDto accountDto, BindingResult bindingResult,
                                HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws
            MessagingException, UnsupportedEncodingException {
        accountDto.validate(accountDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("fail", "Wrong input, please check");
            model.addAttribute("accountDto", accountDto);
            return "admin/employee/create-account";
        }
        if (accountService.findByEmail(accountDto.getEmail()) != null || accountService.findByUserName(accountDto.getUsername()) != null || bindingResult.hasErrors()) {
            if (accountService.findByUserName(accountDto.getUsername()) != null) {
                model.addAttribute("user", "This username already exists!");
            }
            if (accountService.findByEmail(accountDto.getEmail()) != null) {
                model.addAttribute("email", "This email already exists!");
            }
            if (bindingResult.hasErrors()) {
                model.addAttribute("fail", "Wrong input, please check");
                model.addAttribute("accountDto", accountDto);
            }
            return "admin/employee/create-account";
        }

        Account accountUser = new Account();
        BeanUtils.copyProperties(accountDto, accountUser);
        accountUser.setPassword(BCrypt.hashpw(accountUser.getPassword(), BCrypt.gensalt(12)));
        accountUser.setExpiryDate(calculateExpiryDate());
        Role role = new Role();
        role.setId(2);
        accountUser.setRole(role);
        System.out.println(accountUser.getVerificationCode());
        accountService.createAccount(accountUser);
        Employee employee = new Employee();
        employee.setAccount(accountUser);
        employeeService.save(employee);
        String siteURL = getSiteURL(request);
        accountService.sendVerificationEmail(accountUser, siteURL);
        redirectAttributes.addFlashAttribute("message", "Created Account Employee successfully!!." +
                "Please check your gmail to confirm your subscription");
        return "redirect:/admins/employee";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("title", "Create Detail");
        model.addAttribute("employeeDto", employeeDto);
        return "admin/employee/create-employee";
    }

    @PostMapping("/create")
    public String createEmployee(@RequestParam String image, @Validated EmployeeDto employeeDto, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                 Principal principal) {
        String name = principal.getName();
        Account account = accountService.findByUserName(name);
        new EmployeeDto().validate(employeeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/employee/create-employee";
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        employee.setAccount(account);
        employee.setImagePath(image);
        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("message", "Create new employee successfully");
        return "redirect:/employee";
    }

    @GetMapping("/edit/{id}/{email}")
    public String showEditForm(Model model, @PathVariable int id, @PathVariable String email) {
        Employee employee = employeeService.findById(id);
        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee, employeeDto);
        model.addAttribute("title", "Edit Detail");
        model.addAttribute("image", employeeDto.getImagePath());
        model.addAttribute("email", email);
        model.addAttribute("employeeDto", employeeDto);
        return "admin/employee/edit-employee";
    }

    @PostMapping("/edit")
    public String editEmployee(@RequestParam String email, @RequestParam String image,
                               @Validated EmployeeDto employeeDto, @RequestParam int id,
                               RedirectAttributes redirectAttributes, BindingResult bindingResult, Model model) {

        new EmployeeDto().validate(employeeDto, bindingResult);
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        if (bindingResult.hasErrors()) {
            Employee employee1 = employeeService.findById(id);
            String currentImage = employee1.getImagePath();
            Account account = accountService.findByEmail(email);
            model.addAttribute("image", currentImage);
            model.addAttribute("email", account.getEmail());
            return "admin/employee/edit-employee";
        } else {
            Account account = accountService.findByEmail(email);
            if (Objects.equals(image, "")) {
                Employee employee1 = employeeService.findById(id);
                String currentImage = employee1.getImagePath();
                employee.setImagePath(currentImage);
                employee.setAccount(account);
            } else {
                employee.setAccount(account);
                employee.setImagePath(image);
            }
            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("message", "Edited employee successfully");
            return "redirect:/admins/employee";
        }
    }


    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam int code) {
        employeeService.deleteById(code);
        return "redirect:/admins/employee";
    }
    @PostMapping("/recover")
    public String recoverEmployee(@RequestParam int code1) {
        employeeService.recoverEmployee(code1);
        return "redirect:/admins/employee";
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 1);
        return new Date(cal.getTime().getTime());
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
