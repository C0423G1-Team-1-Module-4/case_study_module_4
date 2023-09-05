package com.example.case_study_module_4.service.imp.emloyee;


import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.model.emloyee.Employee;
import com.example.case_study_module_4.repository.emloyee.IEmployeeRepository;
import com.example.case_study_module_4.service.emloyee.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public Page<Employee> findAll(Pageable pageable, String searchName) {
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
}
