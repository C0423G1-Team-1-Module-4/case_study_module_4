package com.example.case_study_module_4.service.imp.booking;

import com.example.case_study_module_4.model.booking.Booking;
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
    public List<Booking> findAll() {
        return rentRepository.findAll();
    }

    @Override
    public Optional<Booking> findById(Integer id) {
        return rentRepository.findById(id);
    }

    @Override
    public void save(Booking booking) {
        rentRepository.save(booking);
    }

    @Override
    public void remove(Integer id) {
        rentRepository.deleteById(id);
    }
}
