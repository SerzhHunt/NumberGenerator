package ru.inovus.numbergenerator.service.util.impl;

import org.springframework.stereotype.Component;
import ru.inovus.numbergenerator.dto.RegistrationNumberDto;
import ru.inovus.numbergenerator.service.util.IncrementAble;

@Component
public class NumberPlateIncrement implements IncrementAble<RegistrationNumberDto> {
    private static final int MAX_CAR_NUMBER = 999;

    @Override
    public RegistrationNumberDto increment(RegistrationNumberDto registrationNumberDto) {
        Integer oldCarNumber = Integer.parseInt(registrationNumberDto.getRegistrationPlate().substring(1, 4));

        if (oldCarNumber < MAX_CAR_NUMBER) {
            int tmp = oldCarNumber;
            int newCarNumber = ++oldCarNumber;
            oldCarNumber = tmp;

            registrationNumberDto.setRegistrationPlate(registrationNumberDto.getRegistrationPlate()
                    .replace(String.valueOf(oldCarNumber), String.valueOf(newCarNumber)));
        } else {
            registrationNumberDto.setRegistrationPlate(registrationNumberDto.getRegistrationPlate()
                    .replace(String.valueOf(oldCarNumber), "001"));
        }
        return registrationNumberDto;
    }
}
