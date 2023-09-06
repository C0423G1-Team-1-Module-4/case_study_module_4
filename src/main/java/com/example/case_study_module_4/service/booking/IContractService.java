package com.example.case_study_module_4.service.booking;

import com.example.case_study_module_4.model.booking.Contract;
import com.example.case_study_module_4.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IContractService extends IGenerateService<Contract> {
    Page<Contract> findAllBySearch(Pageable pageable, String search, String sort, String condition);

    List<Contract> findContract();

    Page<Contract> findContractBySearchReturn(Pageable pageable,  @Param("search") String search,  @Param("sort") String sort, @Param("condition") String condition);
}
