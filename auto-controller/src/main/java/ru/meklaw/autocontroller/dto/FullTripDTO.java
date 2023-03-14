package ru.meklaw.autocontroller.dto;

import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullTripDTO {
    List<TripDTO> trips;
    ZonedDateTime startDateTime;
    ZonedDateTime endDateTime;
    GpsPointDTO startPoint;
    GpsPointDTO endPoint;

    public void changeTimeWithZone(ZoneId zoneId) {
        startDateTime = startDateTime.withZoneSameInstant(zoneId);
        endDateTime = endDateTime.withZoneSameInstant(zoneId);
    }
}
