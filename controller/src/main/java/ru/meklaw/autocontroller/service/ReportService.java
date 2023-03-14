package ru.meklaw.autocontroller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.meklaw.autocontroller.dto.*;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ZoneId localZoneId;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReportService(ZoneId localZoneId,
                         JdbcTemplate jdbcTemplate) {
        this.localZoneId = localZoneId;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Report generateReport(GenerateReport generateReport) {
        if (generateReport.getType() == ReportType.CAR) {
            return generateCarReport(generateReport);
        }
        throw new RuntimeException("Данного типа отчёта не существет");
    }

    private VehicleMileageReport generateCarReport(GenerateReport generateReport) {
        VehicleMileageReport resultReport = new VehicleMileageReport(generateReport.getPeriod(),
                generateReport.getStartTime(),
                generateReport.getEndTime());
        System.out.println(generateReport.getPeriod());
        System.out.println(generateReport.getPeriod()
                                         .toString());
        TreeMap<String, Long> resultMap = jdbcTemplate.query(getSqlRequest(generateReport.getPeriod()),
                                                              (rs, rowNum) -> Map.entry(rs.getString(generateReport.getPeriod()
                                                                                                                   .toString()), rs.getLong("total_length_km")),
                                                              new Object[]{generateReport.getVehicleId(),
                                                                           Timestamp.from(generateReport.getStartTime()
                                                                                                        .toInstant()),
                                                                           Timestamp.from(generateReport.getEndTime()
                                                                                                        .toInstant())})
                                                      .stream()
                                                      .collect(Collectors.toMap(Map.Entry::getKey,
                                                              Map.Entry::getValue,
                                                              (e1, e2) -> e1,
                                                              TreeMap::new));

        resultReport.setResult(resultMap);
        resultReport.changeTimeWithZone(localZoneId);
        return resultReport;
    }

    private String getSqlRequest(Period period) {
        if (period == Period.DAY) {
            return "select to_char(end_time_utc, 'YYYY-MM-DD') AS DAY, " + "       sum(length_km) AS total_length_km " + "from trip " + "where vehicle_id = ? and " + "      start_time_utc >= ? and " + "      end_time_utc <= ? " + "group by to_char(start_time_utc, 'YYYY-MM-DD'), DAY";
        }

        if (period == Period.MONTH) {
            return "select to_char(end_time_utc, 'YYYY-MM') AS MONTH, " + "       sum(length_km) AS total_length_km " + "from trip " + "where vehicle_id = ? and " + "      start_time_utc >= ? and " + "      end_time_utc <= ? " + "group by to_char(start_time_utc, 'YYYY-MM'), MONTH";
        }

        if (period == Period.YEAR) {
            return "select to_char(end_time_utc, 'YYYY') AS YEAR, " + "       sum(length_km) AS total_length_km " + "from trip " + "where vehicle_id = ? and " + "      start_time_utc >= ? and " + "      end_time_utc <= ? " + "group by to_char(start_time_utc, 'YYYY'), YEAR";
        }

        throw new RuntimeException("This period doesn't exist");
    }
}
