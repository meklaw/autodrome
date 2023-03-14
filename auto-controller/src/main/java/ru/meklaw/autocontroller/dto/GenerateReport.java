package ru.meklaw.autocontroller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenerateReport {
    private long vehicleId;
    private ReportType type;
    private Period period;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}
