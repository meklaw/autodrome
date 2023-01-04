package ru.meklaw.autodrome.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.meklaw.autodrome.models.Enterprise;
import ru.meklaw.autodrome.models.Vehicle;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {
    private Long id;

    private String name;

    private int salary;

    private boolean isActive;

    private Long enterprise;

    private Long vehicle;
}
