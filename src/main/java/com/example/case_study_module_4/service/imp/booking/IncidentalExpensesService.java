package com.example.case_study_module_4.service.imp.booking;

import com.example.case_study_module_4.model.booking.IncidentalExpenses;
import com.example.case_study_module_4.repository.booking.IIncidentalExpensesRepository;
import com.example.case_study_module_4.service.booking.IIncidentalExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentalExpensesService implements IIncidentalExpensesService {

    @Autowired
    private IIncidentalExpensesRepository incidentalExpensesRepository;

    @Override
    public List<IncidentalExpenses> findAll() {
        return incidentalExpensesRepository.findAll();
    }

    @Override
    public Optional<IncidentalExpenses> findById(Integer id) {
        return incidentalExpensesRepository.findById(id);
    }

    @Override
    public void save(IncidentalExpenses incidentalExpenses) {
        incidentalExpensesRepository.save(incidentalExpenses);
    }

    @Override
    public void remove(Integer id) {
        incidentalExpensesRepository.deleteById(id);
    }
}
