package ru.meklaw.autodrome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.meklaw.autodrome.models.GpsPoint;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoutingTripPoints {
    List<GpsPoint> points;
    double distanceMeters;
}
