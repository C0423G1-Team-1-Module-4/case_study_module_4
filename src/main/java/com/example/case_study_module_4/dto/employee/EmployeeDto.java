package com.example.case_study_module_4.dto.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto implements Validator {
    private int id;
    private String employeeName;
    private String idCard;
    @NotEmpty(message = "Not Empty")
    private String address;
    private int gender;
    @NotEmpty(message = "Not Empty")
    private String birthdate;
    @Min(value = 1, message = "Must > 0")
    private double salary;
    private String email;
    private String imagePath;
    private boolean status;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDto employeeDto = (EmployeeDto) target;
        if (employeeDto.getEmployeeName().equals("")) {
            errors.rejectValue("employeeName", null, "Not Empty");
        } else if (!employeeDto.getEmployeeName().matches("^[A-Z][a-z]+(\\s[A-Z][a-z]+)+$")) {
            errors.rejectValue("employeeName", null, "1-200 Character");
        }
        if (employeeDto.getIdCard().equals("")) {
            errors.rejectValue("idCard", null, "Not Empty");
        } else if (!employeeDto.getIdCard().matches("^\\d{12}$")) {
            errors.rejectValue("idCard", null, "ID card can be 12 numbers");
        }
    }
}
