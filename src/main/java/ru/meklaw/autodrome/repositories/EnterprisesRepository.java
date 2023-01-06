package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.Enterprise;
import ru.meklaw.autodrome.models.Manager;

import java.util.List;
import java.util.Set;

@Repository
public interface EnterprisesRepository extends JpaRepository<Enterprise, Long> {
    List<Enterprise> findAllByManagersIn(Set<Manager> managers);
}
