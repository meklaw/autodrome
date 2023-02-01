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
public class GpsPointDTO {
    private Long id;

    private ZonedDateTime dateTime;

    private double lat;

    private double lon;

    private String address;

    public void changeTimeWithZone(ZoneId zoneId) {
        dateTime = dateTime.withZoneSameInstant(zoneId);
    }
}
