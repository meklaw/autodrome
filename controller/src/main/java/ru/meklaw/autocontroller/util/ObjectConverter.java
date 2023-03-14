package ru.meklaw.autocontroller.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.meklaw.autocontroller.dto.DriverDTO;
import ru.meklaw.autocontroller.dto.EnterpriseDTO;
import ru.meklaw.autocontroller.dto.GpsPointDTO;
import ru.meklaw.autocontroller.dto.VehicleDTO;
import ru.meklaw.autocontroller.models.Driver;
import ru.meklaw.autocontroller.models.Enterprise;
import ru.meklaw.autocontroller.models.Vehicle;
import ru.meklaw.autocontroller.service.VehiclesService;

import java.util.*;

@Component
public class ObjectConverter {
    private final ModelMapper modelMapper;
    private final VehiclesService vehiclesService;

    public ObjectConverter(ModelMapper modelMapper, VehiclesService vehiclesService) {
        this.modelMapper = modelMapper;
        this.vehiclesService = vehiclesService;
    }

    public DriverDTO convertToDriverDTO(Driver driver) {
        return modelMapper.map(driver, DriverDTO.class);
    }

    public EnterpriseDTO convertToEnterpriseDTO(Enterprise enterprise) {
        return modelMapper.map(enterprise, EnterpriseDTO.class);
    }

    public VehicleDTO convertToVehicleDTO(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    public Vehicle convertToVehicle(VehicleDTO dto) {
        Vehicle vehicle = modelMapper.map(dto, Vehicle.class);

        vehiclesService.enriseBrand(vehicle, dto);
        vehiclesService.enriseEnterprise(vehicle, dto);

        return vehicle;
    }

    public Object convertToGeoJSON(List<GpsPointDTO> gpsPoints) {
        List<Map<String, Object>> features = new ArrayList<>();

        for (GpsPointDTO point : gpsPoints) {
            Map<String, Object> feature = new HashMap<>();
            Map<String, Object> geometry = new HashMap<>();
            Map<String, Object> properties = new HashMap<>();

            geometry.put("type", "Point");
            geometry.put("coordinates", Arrays.asList(point.getLat(), point.getLon()));

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
