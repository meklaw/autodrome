package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.Enterprise;

@Repository
public interface EnterprisesRepository extends JpaRepository<Enterprise, Long> {
}
