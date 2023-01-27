package ru.meklaw.autodrome.dto;

import lombok.*;
import ru.meklaw.autodrome.models.Trip;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GpsTripDTO {
    List<Trip> trips;
    ZonedDateTime startDateTime;
    ZonedDateTime endDateTime;
    GpsPointDTO startPoint;
    GpsPointDTO endPoint;
}
