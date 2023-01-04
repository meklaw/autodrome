package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.Manager;
import ru.meklaw.autodrome.models.Vehicle;

import java.util.List;
import java.util.Set;

@Repository
public interface VehiclesRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findAllByEnterprise_ManagersIn(Set<Manager> enterprise_managers);
}
