package com.example.case_study_module_4.dto.customer;

import com.example.case_study_module_4.dto.employee.EmployeeDto;
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
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto implements Validator {

    private int id;

    private String name;
    @NotEmpty(message = "Not Empty")
    private String phone;
    @NotEmpty(message = "Not Empty")
    private String idCard;
    @NotEmpty(message = "Not Empty")
    private String driverLicense;

    private int verification;

    private int gender;
    @NotEmpty
    private String birthdate;

    private String imageDriverLicenseFront;

    private String imageDriverLicenseBack;

    private String imageIdCardFront;

    private String imageIdCardBack;

    private String email;

    private boolean status;

    private String avatar;
    public boolean isAgeValid() {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusYears(18);
        String date = getBirthdate();
        LocalDate birth = LocalDate.parse(date);
        if (localDate.isAfter(birth)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerDto customerDto = (CustomerDto) target;
        if (customerDto.getName().equals("")) {
            errors.rejectValue("name", null, "Not Empty");
        } else if (!customerDto.getName().matches("^[A-Z][a-z]+(\\s[A-Z][a-z]+)+$")) {
            errors.rejectValue("name", null, "First letter must be capital");
        }
        if (customerDto.getIdCard().equals("")) {
            errors.rejectValue("idCard", null, "Not Empty");
        } else if (!customerDto.getIdCard().matches("^\\d{12}$")) {
            errors.rejectValue("idCard", null, "ID card can be 12 numbers");
        }
        if (!customerDto.isAgeValid()){
            errors.rejectValue("birthdate", null, "Must be greater than 18");
        }
    }
}
