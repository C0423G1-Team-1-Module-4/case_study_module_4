package com.example.case_study_module_4.service.customer;

import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


public interface ICustomerService extends IGenerateService<Customer> {
    Page<Customer> findAllCustomer(String searchName, Pageable pageable);
}
