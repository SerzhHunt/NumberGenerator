package ru.inovus.numbergenerator.dto;

import lombok.Data;
import ru.inovus.numbergenerator.model.Country;


@Data
public class RegistrationNumberDto {

    private String registrationPlate;
    private Integer region;
    private Country country;

    @Override
    public String toString() {
        return registrationPlate + " " + region + country;
    }
}
