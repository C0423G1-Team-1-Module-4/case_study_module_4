package com.example.case_study_module_4.model.customer;

import com.example.case_study_module_4.account.model.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String idCard;

    private String driverLicense;

    private String verification;

    private int gender;

    private String birthdate;

    private String imageDriverLicense;

    private String imageIdCard;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}

