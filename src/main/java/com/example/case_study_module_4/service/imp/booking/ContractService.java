package com.example.case_study_module_4.service.imp.booking;

import com.example.case_study_module_4.model.booking.Contract;
import com.example.case_study_module_4.repository.booking.IContractRepository;
import com.example.case_study_module_4.service.booking.IContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService implements IContractService {

    @Autowired
    private IContractRepository contractRepository;

    @Override
    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    @Override
    public Optional<Contract> findById(Integer id) {
        return contractRepository.findById(id);
    }

    @Override
    public void save(Contract contract) {
        contractRepository.save(contract);
    }

    @Override
    public void remove(Integer id) {
        contractRepository.deleteById(id);
    }

    @Override
    public Page<Contract> findAllBySearch(Pageable pageable, String search, String sort, String condition) {
        return contractRepository.findContractBySearch(pageable, "%" + search + "%", sort, condition);
    }
}
