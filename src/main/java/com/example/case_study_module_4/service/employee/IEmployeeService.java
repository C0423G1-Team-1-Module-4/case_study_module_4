package com.example.case_study_module_4.service.employee;

import com.example.case_study_module_4.account.model.Account;
import com.example.case_study_module_4.dto.employee.IEmployeeDto;
import com.example.case_study_module_4.model.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {
    Page<IEmployeeDto> findAll(Pageable pageable, String searchName, String sortProperty, String condition);

    void save(Employee employee);

    Employee findById(int id);

    void deleteById(int code);

    Employee getEmployeeByAccount(Account account);

    void recoverEmployee(int code1);
}
