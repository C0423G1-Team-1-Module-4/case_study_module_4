package com.example.case_study_module_4.service.customer;


import com.example.case_study_module_4.dto.customer.ICustomerDto;

import com.example.case_study_module_4.account.model.Account;

import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


public interface ICustomerService extends IGenerateService<Customer> {

    Page<ICustomerDto> findAllCustomer(String searchName, Pageable pageable);

    Customer findCustomerByAccount(Account account);

    void deleteById(int code);
}
