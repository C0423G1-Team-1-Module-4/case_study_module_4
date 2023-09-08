package com.example.case_study_module_4.dto.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto implements Validator {

    private int id;

    private String name;
    @NotEmpty(message = "Not Empty")
    private String idCard;
    @NotEmpty(message = "Not Empty")
    private String driverLicense;

    private int verification;

    private int gender;
    @NotEmpty
    private String birthdate;

    private String imageDriverLicense;

    private String imageIdCard;

    private String email;

    private int status;

    private String avatar;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerDto customerDto = (CustomerDto) target;
        if (customerDto.getName().equals("")) {
            errors.rejectValue("name", null, "Not Empty");
        } else if (!customerDto.getName().matches("^(?!\\s)(?!.*\\s$).{1,200}$")) {
            errors.rejectValue("employeeName", null, "1-200 Character");
        } else if (!customerDto.getName().matches("^[^\\W_@;,.=\\-+…]+$")) {
            errors.rejectValue("employeeName", null, "Dont have @ ; , . = - +");
        }
        if (customerDto.getIdCard() .equals("")) {
            errors.rejectValue("idCard", null, "Not Empty");
        } else if (!customerDto .getIdCard().matches("^\\d{12}$")) {
            errors.rejectValue("idCard", null, "ID card can be 12 numbers");
        }
    }
}
