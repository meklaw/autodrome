package ru.meklaw.autodrome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    private ZonedDateTime buyDateTimeUtc;

    public void changeTimeWithZone(ZoneId zoneId) {
        buyDateTimeUtc = buyDateTimeUtc.withZoneSameInstant(zoneId);
    }
}