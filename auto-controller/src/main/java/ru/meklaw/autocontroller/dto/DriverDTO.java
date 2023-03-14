package ru.meklaw.autocontroller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {
    private Long id;

    private String name;

    private int salary;

    private boolean isActive;

    private Long enterpriseId;

    private Long vehicleId;
}
