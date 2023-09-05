package com.example.case_study_module_4.dto.booking;

import com.example.case_study_module_4.model.booking.Booking;
import com.example.case_study_module_4.model.booking.CollateralAssets;
import com.example.case_study_module_4.model.emloyee.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Getter
@Setter
@NoArgsConstructor
public class ContractDto implements Validator {
    private int id;

    private Booking booking;

    private int rentalFee;//phi thue

    private CollateralAssets collateralAsset;

    private String receiveAddress;

    private String contractCreationDate;

    private Employee employee;

    private int status_confirm;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ContractDto contractDto = (ContractDto) target;

        if (contractDto.receiveAddress == null) {
            errors.rejectValue("receiveAddress", "invalid", "Địa chỉ không được để trống");
        } else if (contractDto.receiveAddress.equals("")) {
            errors.rejectValue("receiveAddress", "invalid", "Địa chỉ không được để trống");
        }
    }
}
