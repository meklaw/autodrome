package ru.meklaw.autodrome.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number")
    private String number;
    @Column(name = "cost")
    private int cost;
    @Column(name = "year_of_production")
    private int yearOfProduction;
    @Column(name = "mileage")
    private int mileage;

    @Column(name = "buy_date_time_utc")
    private ZonedDateTime buyDateTimeUtc;
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private VehicleBrand brand;

    @ManyToOne
    @JoinColumn(name = "enterprise_id", referencedColumnName = "id")
    private Enterprise enterprise;

    //    TODO remove CascadeType and change database to SET NULL when delete
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Driver> drivers;

    public ZonedDateTime getBuyDateTimeUtc() {
        return buyDateTimeUtc.withZoneSameLocal(ZoneId.of("UTC"));
    }

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<GpsPoint> points;

}
