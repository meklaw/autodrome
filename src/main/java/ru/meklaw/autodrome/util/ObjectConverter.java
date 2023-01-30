package ru.meklaw.autodrome.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.meklaw.autodrome.dto.GpsPointDTO;
import ru.meklaw.autodrome.models.GpsPoint;

import java.util.*;

@Component
public class ObjectConverter {
    private final ModelMapper modelMapper;

    public ObjectConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public GpsPointDTO convertToGpsPointDTO(GpsPoint point) {
        return modelMapper.map(point, GpsPointDTO.class);
    }

    public Object convertToGeoJSON(List<GpsPointDTO> gpsPoints) {
        List<Map<String, Object>> features = new ArrayList<>();

        for (GpsPointDTO point : gpsPoints) {
            Map<String, Object> feature = new HashMap<>();
            Map<String, Object> geometry = new HashMap<>();
            Map<String, Object> properties = new HashMap<>();

            geometry.put("type", "Point");
            geometry.put("coordinates", Arrays.asList(point.getX(), point.getY()));

            properties.put("id", point.getId());
            properties.put("dateTime", point.getDateTime());

            feature.put("type", "Feature");
            feature.put("geometry", geometry);
            feature.put("properties", properties);

            features.add(feature);
        }

        Map<String, Object> geoJSON = new HashMap<>();
        geoJSON.put("type", "FeatureCollection");
        geoJSON.put("features", features);

        return geoJSON;
    }
}
