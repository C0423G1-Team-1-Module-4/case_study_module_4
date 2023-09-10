package com.example.case_study_module_4.repository.customer;


import com.example.case_study_module_4.dto.customer.ICustomerDto;

import com.example.case_study_module_4.account.model.Account;

import com.example.case_study_module_4.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "select cus.id, cus.name, cus.id_card as idCard, cus.gender," +
            " cus.birthdate, acc.email, acc.status " +
            "from customer as cus join account as acc " +
            "on acc.id = cus.account_id" +
            "  where name like :name ", nativeQuery = true)
    Page<ICustomerDto> findAllCustomer(@Param("name") String name, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update account as acc " +
            "join customer as cus on cus.account_id = acc.id " +
            "set acc.status = b'0' " +
            "where cus.id = :id",nativeQuery = true)
    void  deleteById(@Param("id")int id);


    Customer findCustomerByAccount(Account account);


}
