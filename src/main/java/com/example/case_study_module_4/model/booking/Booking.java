package com.example.case_study_module_4.model.booking;

import javax.persistence.*;

import com.example.case_study_module_4.model.customer.Customer;
import com.example.case_study_module_4.model.emloyee.Employee;
import com.example.case_study_module_4.model.product.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String receiveDate;

    private String returnDate;

    private int rentalPrice;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}

