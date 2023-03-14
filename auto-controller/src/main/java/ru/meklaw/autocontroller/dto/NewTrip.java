package ru.meklaw.autocontroller.dto;

import lombok.Builder;
import lombok.Getter;
import ru.meklaw.autocontroller.models.GpsPoint;
import ru.meklaw.autocontroller.models.Trip;

import java.util.List;

@Builder
@Getter
public class NewTrip {
    List<GpsPoint> points;
    List<Trip> trips;
}
