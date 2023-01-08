package ru.meklaw.autodrome.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FillEnterprisesDTO {
    private Long enterpriseId;

    private int countCars;
}
