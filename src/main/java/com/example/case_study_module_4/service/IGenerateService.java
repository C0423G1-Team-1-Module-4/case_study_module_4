package com.example.case_study_module_4.service;

import com.example.case_study_module_4.model.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface IGenerateService<T> {
    List<T> findAll();

    Optional<T> findById(Integer id);

    void save(Optional<Customer> t);

    void remove(Integer id);
}
