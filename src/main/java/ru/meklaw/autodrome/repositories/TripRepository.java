package ru.meklaw.autodrome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meklaw.autodrome.models.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
