package com.example.case_study_module_4.controller.employee;

import com.example.case_study_module_4.account.model.Account;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping("/employee")
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
//        Page<IEmployeeDto> employeeDtos = null;
//        if (Objects.equals(sort, "")) {
//            Pageable pageable = PageRequest.of(page, 2);
//            employeeDtos = employeeService.findAll(pageable, searchName);
//        } else if (Objects.equals(sort, "up")) {
//            Pageable pageable = PageRequest.of(page, 2, Sort.by("employee_name").ascending());
//            employeeDtos = employeeService.findAll(pageable, searchName);
//        } else if (Objects.equals(sort, "down")){
//            Pageable pageable = PageRequest.of(page, 2, Sort.by("employee_name").descending());
//            employeeDtos = employeeService.findAll(pageable, searchName);
//        }else if (Objects.equals(sort, "saup")){
//            Pageable pageable = PageRequest.of(page, 2, Sort.by("salary").ascending());
//            employeeDtos = employeeService.findAll(pageable, searchName);
//        }else if (Objects.equals(sort, "sadown")){
//            Pageable pageable = PageRequest.of(page, 2, Sort.by("salary").descending());
//            employeeDtos = employeeService.findAll(pageable, searchName);
//        }
//        model.addAttribute("title", "View Detail");
//        model.addAttribute("searchName", searchName);
//        model.addAttribute("employeeDtos", employeeDtos);
//        return "admin/employee/list-employee-2";
        if (sortProperty == null || sortProperty.isEmpty()) {
            sortProperty = "employee_name";
        }
        if (condition == null || condition.isEmpty()) {
            condition = "desc";
        }
        Sort sort = Sort.by(condition.equalsIgnoreCase("asc") ? Sort.Order.asc(sortProperty) : Sort.Order.desc(sortProperty));
        Pageable pageable = PageRequest.of(page, 2);
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);


        Page<IEmployeeDto> employeeDtos = employeeService.findAll(pageable, searchName, sortProperty, condition);
        model.addAttribute("title", "View Detail");
        model.addAttribute("searchName", searchName);
        model.addAttribute("employeeDtos", employeeDtos);
        return "admin/employee/list-employee-2";
    }
//    @GetMapping("/createAccount")
//    public String showCreateAccount(Model model){
//
//    }

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

    @GetMapping("/edit/{id}")
    public String showEditForm(Model model, @PathVariable int id) {
        Employee employee = employeeService.findById(id);
        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee, employeeDto);
        model.addAttribute("title", "Edit Detail");
        model.addAttribute("image", employeeDto.getImagePath());
        model.addAttribute("employeeDto", employeeDto);
        return "admin/employee/edit-employee";
    }

    @PostMapping("/edit")
    public String editEmployee(Principal principal, @RequestParam String image, @Validated EmployeeDto employeeDto, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        new EmployeeDto().validate(employeeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/employee/edit-employee";
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        Account account = accountService.findByUserName(principal.getName());
        employee.setAccount(account);
        employee.setImagePath(image);
        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("message", "Edited employee successfully");
        return "redirect:/employee";
    }

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam int code) {
        employeeService.deleteById(code);
        return "redirect:/employee";
    }
}
