package com.example.case_study_module_4.service.employee;

import com.example.case_study_module_4.dto.employee.IEmployeeDto;
import com.example.case_study_module_4.model.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {
    Page<IEmployeeDto> findAll(Pageable pageable, String searchName);

    void save(Employee employee);

    Employee findById(int id);

    void deleteById(int code);
}
