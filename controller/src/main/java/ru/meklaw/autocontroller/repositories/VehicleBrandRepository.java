package ru.meklaw.autocontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autocontroller.models.VehicleBrand;

@Repository
public interface VehicleBrandRepository extends JpaRepository<VehicleBrand, Long> {
}
