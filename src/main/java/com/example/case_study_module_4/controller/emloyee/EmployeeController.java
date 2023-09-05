package com.example.case_study_module_4.controller.emloyee;

import com.example.case_study_module_4.dto.emloyee.EmployeeDto;
import com.example.case_study_module_4.model.emloyee.Employee;

import com.example.case_study_module_4.service.emloyee.IEmployeeService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("")
    public String showList(@RequestParam(defaultValue = "0", required = false) int page,
                           @RequestParam(defaultValue = "", required = false) String searchName,
                           Model model) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("employee_name").ascending());
        Page<Employee> employees = employeeService.findAll(pageable, searchName);
        model.addAttribute("title", "View Detail");
        model.addAttribute("employees", employees);
        return "admin/employee/list-employee";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employeeDto", employeeDto);
//        model.addAttribute("account", account);
        return "admin/employee/create-employee";
    }

    @PostMapping("/create")
    public String createEmployee(EmployeeDto employeeDto, RedirectAttributes redirectAttributes) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("message", "Create new Employee successfully");
        return "redirect:/employee";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(Model model, @PathVariable int id) {
        Employee employee = employeeService.findById(id);
        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee,employeeDto);
        model.addAttribute("employeeDto", employeeDto);
        return "admin/employee/edit-employee";
    }
    @PostMapping("/edit")
    public String editEmployee(EmployeeDto employeeDto, RedirectAttributes redirectAttributes) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        employeeService.save(employee);
        redirectAttributes.addFlashAttribute("message", "Create new Employee successfully");
        return "redirect:/employee";
    }
}
