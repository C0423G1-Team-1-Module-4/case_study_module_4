package com.example.case_study_module_4.model.emloyee;

import com.example.case_study_module_4.account.model.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String employeeName;

    private int salary;

    private String address;

    private String idCard;

    private int gender;

    private String birthdate;

    private String imagePath;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}

