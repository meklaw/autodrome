package ru.meklaw.autocontroller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenerateTrip {
    private long vehicleId;
    private int lengthKm;
    private double maxSpeedKph;
    private double acceleration;
}
