package ru.meklaw.autodrome.dto;

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
    ReportType type;
    Period reportingPeriod;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}
