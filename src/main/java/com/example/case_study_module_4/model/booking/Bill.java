package com.example.case_study_module_4.model.booking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String actualReceiveDate;

    private String actualReturnDate;

    private int totalAmount;

    @OneToMany(mappedBy = "bill")
    private List<IncidentalExpenses> incidentalExpensesList;

    @OneToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private Contract contract;
}

