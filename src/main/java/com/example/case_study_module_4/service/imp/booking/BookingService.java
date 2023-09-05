package com.example.case_study_module_4.service.imp.booking;

import com.example.case_study_module_4.model.booking.Booking;
import com.example.case_study_module_4.repository.booking.IBookingRepository;
import com.example.case_study_module_4.service.booking.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findById(Integer id) {
        return bookingRepository.findById(id);
    }

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void remove(Integer id) {
        bookingRepository.deleteById(id);
    }
}
