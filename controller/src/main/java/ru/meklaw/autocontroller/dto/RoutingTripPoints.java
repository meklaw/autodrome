package ru.meklaw.autocontroller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.meklaw.autocontroller.models.GpsPoint;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoutingTripPoints {
    List<GpsPoint> points;
    double distanceMeters;
}
