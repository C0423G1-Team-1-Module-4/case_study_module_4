package com.example.case_study_module_4.dto.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {
    private int id;
    private String employeeName;
    private String idCard;
    private String address;
    private int gender;
    private String birthdate;
    private double salary;
    private String email;
    private String imagePath;
    private int status;
}
