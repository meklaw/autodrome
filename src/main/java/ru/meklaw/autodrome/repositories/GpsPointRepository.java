package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.GpsPoint;

@Repository
public interface GpsPointRepository extends JpaRepository<GpsPoint, Long> {
}
