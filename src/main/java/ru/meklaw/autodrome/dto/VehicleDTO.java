package ru.meklaw.autodrome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    private Long id;
    private String number;
    private int cost;
    private int yearOfProduction;
    private int mileage;
    private Long vehicleBrandId;
    private Long enterpriseId;
}