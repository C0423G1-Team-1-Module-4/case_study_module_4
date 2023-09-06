package com.example.case_study_module_4.service.imp.booking;

import com.example.case_study_module_4.model.booking.CollateralAssets;
import com.example.case_study_module_4.repository.booking.ICollateralAssetsRepository;
import com.example.case_study_module_4.service.booking.ICollateralAssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollateralAssetsService implements ICollateralAssetsService {

    @Autowired
    private ICollateralAssetsRepository collateralAssetsRepository;

    @Override
    public List<CollateralAssets> findAll() {
        return collateralAssetsRepository.findAll();
    }

    @Override
    public Optional<CollateralAssets> findById(Integer id) {
        return collateralAssetsRepository.findById(id);
    }

    @Override
    public void save(CollateralAssets collateralAssets) {
        collateralAssetsRepository.save(collateralAssets);
    }

    @Override
    public void remove(Integer id) {
        collateralAssetsRepository.deleteById(id);
    }
}
