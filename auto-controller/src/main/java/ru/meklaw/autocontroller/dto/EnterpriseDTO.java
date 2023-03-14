package ru.meklaw.autocontroller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseDTO {
    private Long id;
    private String name;
    private String city;
    private LocalDate founded;
    private ZoneId timeZone;
}

