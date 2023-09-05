package com.example.case_study_module_4.model.booking;

import javax.persistence.*;

import com.example.case_study_module_4.model.emloyee.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;

    private int rentalFee;//phi thue

    @ManyToOne
    @JoinColumn(name = "collateral_asset_id", referencedColumnName = "id")
    private CollateralAssets collateralAsset;

    private String receiveAddress;

    private String contractCreationDate;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    private int status_confirm;
}

