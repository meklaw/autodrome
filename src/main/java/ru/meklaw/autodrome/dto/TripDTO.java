package ru.meklaw.autodrome.dto;

import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Long id;
    private ZonedDateTime startTimeUtc;
    private ZonedDateTime endTimeUtc;

    public void changeTimeWithZone(ZoneId zoneId) {
        startTimeUtc = startTimeUtc.withZoneSameInstant(zoneId);
        endTimeUtc = endTimeUtc.withZoneSameInstant(zoneId);
    }
}
