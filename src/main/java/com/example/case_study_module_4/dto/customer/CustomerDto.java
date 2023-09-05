package com.example.case_study_module_4.dto.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {

    @Id
    private int id;

    private String idCard;

    private String driverLicense;

    private String verification;

    private int gender;

    private String birthdate;

    private String imageDriverLicense;

    private String imageIdCard;

    private String email;

    private int status;
}
