package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.Driver;
import ru.meklaw.autodrome.models.Manager;

import java.util.List;
import java.util.Set;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByEnterprise_ManagersIn(Set<Manager> managers);
}
