package ru.meklaw.autocontroller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autocontroller.models.Driver;
import ru.meklaw.autocontroller.models.Manager;

import java.util.List;
import java.util.Set;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByEnterprise_ManagersIn(Set<Manager> managers);
}
