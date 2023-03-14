package ru.meklaw.autocontroller.dto;

import java.time.ZonedDateTime;

public class VehicleMileageReport extends Report {
    public VehicleMileageReport(Period period,
                                ZonedDateTime startTime,
                                ZonedDateTime endTime) {
        super(ReportType.CAR, period, startTime, endTime);
    }

}
