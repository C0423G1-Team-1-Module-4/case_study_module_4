package com.example.case_study_module_4.dto.booking;

import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.model.emloyee.Employee;
import com.example.case_study_module_4.model.product.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BookingDto implements Validator {

    private int id;

    private String receiveDate;

    private String returnDate;

    private int rentalPrice;

    private Vehicle vehicle;

    private Customer customer;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookingDto rentDto = (BookingDto) target;

        LocalDate receiveDate = LocalDate.parse(rentDto.getReceiveDate());
        LocalDate returnDate = LocalDate.parse(rentDto.getReturnDate());

        LocalDate currentDate = LocalDate.now();

        if (receiveDate.isBefore(currentDate)) {
            errors.rejectValue("receiveDate", "invalid", "Ngày thuê phải là ngày hôm nay trở đi.");
        }

        if (returnDate.isBefore(receiveDate.plusDays(1))) {
            errors.rejectValue("returnDate", "invalid", "Ngày trả xe phải hơn ít nhất 1 ngày so với ngày thuê.");
        }
    }
}
