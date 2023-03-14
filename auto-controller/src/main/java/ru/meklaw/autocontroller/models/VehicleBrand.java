package ru.meklaw.autocontroller.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "vehicle_brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleBrand {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "tank")
    private Double tank;

    @Column(name = "load_capacity")
    private Double loadCapacity;

    @Column(name = "number_of_places")
    private Integer numberOfPlaces;

    @OneToMany(mappedBy = "brand")
    private List<Vehicle> vehicles;

}

