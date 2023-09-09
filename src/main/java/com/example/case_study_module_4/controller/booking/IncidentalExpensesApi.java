package com.example.case_study_module_4.controller.booking;

import com.example.case_study_module_4.model.booking.Bill;
import com.example.case_study_module_4.model.booking.IncidentalExpenses;
import com.example.case_study_module_4.service.booking.IBillService;
import com.example.case_study_module_4.service.booking.IIncidentalExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/incidentalExpenses")
public class IncidentalExpensesApi {

    @Autowired
    private IIncidentalExpensesService incidentalExpensesService;

    @Autowired
    private IBillService billService;

    @PostMapping("/bill/{id}")
    public ResponseEntity<IncidentalExpenses> create(@RequestBody IncidentalExpenses incidentalExpenses, @PathVariable int id) {
        Bill bill = billService.findById(id).orElse(null);
        incidentalExpenses.setBill(bill);
        return new ResponseEntity<>(incidentalExpensesService.saveObject(incidentalExpenses), HttpStatus.CREATED);
    }

    @PostMapping("/bill/delete/{id}")
    public ResponseEntity<IncidentalExpenses> removeIe(@PathVariable int id) {
        return new ResponseEntity<>(incidentalExpensesService.deleteIncidentalExpensesById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<IncidentalExpenses>> list(@PathVariable int id) {
        List<IncidentalExpenses> expensesList = billService.findById(id).orElse(null).getIncidentalExpensesList();
        if (expensesList == null) {
            expensesList = new ArrayList<>();
        }
        return new ResponseEntity<>(expensesList, HttpStatus.OK);
    }
}
