package ru.inovus.numbergenerator.unit.unit;

import org.junit.jupiter.api.Test;
import ru.inovus.numbergenerator.dto.RegistrationNumberDto;
import ru.inovus.numbergenerator.model.Country;
import ru.inovus.numbergenerator.model.Region;
import ru.inovus.numbergenerator.service.util.impl.NumberPlateIncrement;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberPlateIncrementTests {

    private final NumberPlateIncrement numberPlateIncrement = new NumberPlateIncrement();

    @Test
    void testGetNextRegNumberWithSuccess() {
        getNextRegNumber("002", "001");
        getNextRegNumber("112", "111");
        getNextRegNumber("500", "499");
    }

    @Test
    void shouldReturn001IfNumberReaches999() {
        getNextRegNumber("001", "999");
    }

    private void getNextRegNumber(String expectedNumber, String actualNumber) {
        RegistrationNumberDto increment = numberPlateIncrement.increment(buildRegistrationNumber(actualNumber));
        String newRegNumber = increment.getRegistrationPlate().substring(1,4);
        assertEquals(expectedNumber, newRegNumber);
    }

    private RegistrationNumberDto buildRegistrationNumber(String regNumber) {
        return RegistrationNumberDto.builder()
                .registrationPlate("A".concat(regNumber).concat("AA"))
                .region(Region.TATARSTAN.getRegion())
                .country(Country.RUS)
                .build();
    }
}
