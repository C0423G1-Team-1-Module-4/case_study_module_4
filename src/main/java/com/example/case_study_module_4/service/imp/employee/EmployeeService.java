package com.example.case_study_module_4.service.imp.employee;


import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.dto.employee.IEmployeeDto;
import com.example.case_study_module_4.model.employee.Employee;
import com.example.case_study_module_4.repository.employee.IEmployeeRepository;
import com.example.case_study_module_4.service.employee.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public Page<IEmployeeDto> findAll(Pageable pageable, String searchName) {
        return employeeRepository.findAll(pageable, "%" + searchName + "%");
    }

    @Override
    public void save(Employee employee) {
        Account account = new Account();
        account.setId(1);
        employee.setAccount(account);
        employeeRepository.save(employee);
    }

    @Override
    public Employee findById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(int code) {
        employeeRepository.deleteById(code);
    }
}
