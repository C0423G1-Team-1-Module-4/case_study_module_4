package com.example.case_study_module_4.service.imp.booking;

import com.example.case_study_module_4.model.booking.Rent;
import com.example.case_study_module_4.repository.booking.IRentRepository;
import com.example.case_study_module_4.service.booking.IRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentService implements IRentService {

    @Autowired
    private IRentRepository rentRepository;

    @Override
    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    @Override
    public Optional<Rent> findById(Integer id) {
        return rentRepository.findById(id);
    }

    @Override
    public void save(Rent rent) {
        rentRepository.save(rent);
    }

    @Override
    public void remove(Integer id) {
        rentRepository.deleteById(id);
    }
}
