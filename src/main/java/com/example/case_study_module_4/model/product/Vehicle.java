package com.example.case_study_module_4.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String vehicleName;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", referencedColumnName = "id")
    private VehicleType vehicleType;

    private String transmission;

    private String fuel;

    private String description;

    @OneToMany(mappedBy = "vehicle")
    private List<Image> imageList;

    private int rentalPrice;

    private int status;
}

