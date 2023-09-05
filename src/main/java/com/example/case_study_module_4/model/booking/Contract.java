package com.example.case_study_module_4.model.booking;

import javax.persistence.*;
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

    private int totalAmount;

    private int rentalFee;//phi thue

    private int insuranceFee;//bao hiem

    @ManyToOne
    @JoinColumn(name = "collateral_asset_id", referencedColumnName = "id")
    private CollateralAssets collateralAsset;

    private String receiveAddress;

    private String contractCreationDate;
}

