package com.example.case_study_module_4.service.imp.booking;

import com.example.case_study_module_4.model.booking.Bill;
import com.example.case_study_module_4.repository.booking.IBillRepository;
import com.example.case_study_module_4.service.booking.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService implements IBillService {

    @Autowired
    private IBillRepository billRepository;

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Optional<Bill> findById(Integer id) {
        return billRepository.findById(id);
    }

    @Override
    public void save(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public void remove(Integer id) {
        billRepository.deleteById(id);
    }
}
