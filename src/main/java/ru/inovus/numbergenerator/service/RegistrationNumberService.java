package ru.inovus.numbergenerator.service;

import ru.inovus.numbergenerator.dto.RegistrationNumberDto;
import ru.inovus.numbergenerator.model.Country;
import ru.inovus.numbergenerator.model.Region;

public interface RegistrationNumberService {
    RegistrationNumberDto getRandomRegistrationNumber(Region region, Country country);

    RegistrationNumberDto getNextRegistrationNumber(Region region, Country country);

    RegistrationNumberDto saveRegistrationNumber(RegistrationNumberDto registrationNumberDto);
}
