package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
}
