package ru.meklaw.autodrome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseDTO {
    private Long id;
    private String name;
    private String city;
    private LocalDate founded;
    private List<VehicleDTO> vehicles;
    private List<DriverDTO> drivers;
}

