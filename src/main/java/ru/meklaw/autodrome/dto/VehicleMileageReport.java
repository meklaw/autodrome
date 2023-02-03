package ru.meklaw.autodrome.dto;

import java.time.ZonedDateTime;

public class VehicleMileageReport extends Report {
    public VehicleMileageReport(Period period,
                                ZonedDateTime startTime,
                                ZonedDateTime endTime) {
        super(ReportType.CAR, period, startTime, endTime);
    }

}
