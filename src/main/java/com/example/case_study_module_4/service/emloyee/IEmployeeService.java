package com.example.case_study_module_4.service.emloyee;

import com.example.case_study_module_4.model.emloyee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {
    Page<Employee> findAll(Pageable pageable, String searchName);

    void save(Employee employee);

    Employee findById(int id);
}
