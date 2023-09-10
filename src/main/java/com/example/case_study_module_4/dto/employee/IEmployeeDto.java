package com.example.case_study_module_4.dto.employee;

public interface IEmployeeDto {
//    select ee.id as id, ee.employee_name as employeeName, ee.id_card as idCard, ee.address , " +
//            "ee.gender, ee.birthdate, ee.salary, " +
//            "acc.email, acc.status
    int getId();
    String getEmployeeName();
    String getIdCard();
    String getAddress();
    int getGender();
    String getBirthDate();
    double getSalary();
    String getEmail();
    boolean getStatus();
}
