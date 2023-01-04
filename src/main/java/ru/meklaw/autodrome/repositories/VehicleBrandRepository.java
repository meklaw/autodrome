package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.VehicleBrand;

@Repository
public interface VehicleBrandRepository extends JpaRepository<VehicleBrand, Long> {
}
