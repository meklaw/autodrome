package ru.meklaw.autodrome.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Entity
@Table(name = "enterprise")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enterprise {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "founded")
    private LocalDate founded;
    @Column(name = "time_zone")
    private ZoneId timeZone;


    @OneToMany(mappedBy = "enterprise")
    private List<Vehicle> vehicles;


    @OneToMany(mappedBy = "enterprise")
    private List<Driver> drivers;

    @ManyToMany(mappedBy = "enterprises")
    private List<Manager> managers;
}

