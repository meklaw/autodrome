package ru.meklaw.autodrome.dto;

import lombok.*;
import ru.meklaw.autodrome.models.Trip;

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

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.startTimeUtc = trip.getStartTimeUtc();
        this.endTimeUtc = trip.getEndTimeUtc();
    }
}
