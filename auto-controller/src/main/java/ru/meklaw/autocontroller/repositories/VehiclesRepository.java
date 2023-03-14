package ru.meklaw.autocontroller.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autocontroller.models.Manager;
import ru.meklaw.autocontroller.models.Vehicle;

import java.util.List;
import java.util.Set;

@Repository
public interface VehiclesRepository extends JpaRepository<Vehicle, Long>, PagingAndSortingRepository<Vehicle, Long> {
    List<Vehicle> findAllByEnterprise_ManagersIn(Set<Manager> enterprise_managers, PageRequest of);
    List<Vehicle> findAllByEnterpriseId(long id, PageRequest of);
}
