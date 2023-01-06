package ru.meklaw.autodrome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.meklaw.autodrome.models.VehicleBrand;
import ru.meklaw.autodrome.repositories.VehicleBrandRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleBrandsService {
    private final VehicleBrandRepository vehicleBrandRepository;

    @Autowired
    public VehicleBrandsService(VehicleBrandRepository vehicleBrandRepository) {
        this.vehicleBrandRepository = vehicleBrandRepository;
    }

    public List<VehicleBrand> findAll() {
        return vehicleBrandRepository.findAll();
    }

    public VehicleBrand create(VehicleBrand brand) {
        return vehicleBrandRepository.save(brand);
    }

    public Optional<VehicleBrand> findById(long id) {
        return vehicleBrandRepository.findById(id);
    }

    public void update(long id, VehicleBrand brand) {
        brand.setId(id);
        vehicleBrandRepository.save(brand);
    }

    public void delete(long id) {
        vehicleBrandRepository.deleteById(id);
    }
}

