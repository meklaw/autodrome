package ru.meklaw.autodrome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.TreeMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private ReportType type;
    private Period reportingPeriod;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private TreeMap<ZonedDateTime, String> result;
}
