package ru.meklaw.autodrome.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gps_point")
public class GpsPoint {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;
    @Column(name = "x")
    private double lat;

    @Column(name = "y")
    private double lon;
}

