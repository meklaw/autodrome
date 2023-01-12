package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.Enterprise;
import ru.meklaw.autodrome.models.Manager;

import java.util.Collection;
import java.util.List;

@Repository
public interface EnterprisesRepository extends JpaRepository<Enterprise, Long> {
    List<Enterprise> findAllByManagersIn(Collection<Manager> managers);

    List<Enterprise> findAllByIdIn(Collection<Long> enterprises);
}
