package com.example.case_study_module_4.service.customer;

import com.example.case_study_module_4.account.model.Account;

import com.example.case_study_module_4.dto.customer.ICustomerDto;

import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.repository.customer.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;

    public Page<ICustomerDto> findAllCustomer(String searchName, Pageable pageable, String sortProperty, String condition) {
        return customerRepository.findAllCustomer("%" + searchName + "%", pageable, sortProperty, condition);
    }

    public Customer findCustomerByAccount_Id(int id){
        return customerRepository.findCustomerByAccount_Id(id);
    }
    @Override
    public Customer findCustomerByAccount(Account account) {
        return customerRepository.findCustomerByAccount(account);
    }

    @Override
    public void deleteById(int code) {
        customerRepository.deleteById(code);
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void remove(Integer id) {
        customerRepository.deleteById(id);
    }


}
