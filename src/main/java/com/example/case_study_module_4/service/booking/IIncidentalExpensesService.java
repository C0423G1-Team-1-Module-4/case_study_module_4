package com.example.case_study_module_4.service.booking;

import com.example.case_study_module_4.model.booking.Bill;
import com.example.case_study_module_4.model.booking.IncidentalExpenses;
import com.example.case_study_module_4.service.IGenerateService;

import java.util.List;

public interface IIncidentalExpensesService extends IGenerateService<IncidentalExpenses> {
    IncidentalExpenses saveObject(IncidentalExpenses incidentalExpenses);

    IncidentalExpenses deleteIncidentalExpensesById(int id);

    List<IncidentalExpenses> findAllByBill(Bill bill);
}
