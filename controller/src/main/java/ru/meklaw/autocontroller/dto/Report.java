package ru.meklaw.autocontroller.dto;

import lombok.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TreeMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private ReportType type;
    private Period period;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private TreeMap<String, Long> result;

    public Report(ReportType type, Period period, ZonedDateTime startTime, ZonedDateTime endTime) {
        this.type = type;
        this.period = period;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void changeTimeWithZone(ZoneId zoneId) {
        startTime = startTime.withZoneSameInstant(zoneId);
        endTime = endTime.withZoneSameInstant(zoneId);
    }
}
