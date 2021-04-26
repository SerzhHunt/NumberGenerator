package ru.inovus.numbergenerator.service.util.impl;

import org.springframework.stereotype.Component;
import ru.inovus.numbergenerator.service.util.Generator;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class NumberPlateGenerator implements Generator<String> {
    private static final int MIN_CAR_NUMBER = 1;
    private static final int MAX_CAR_NUMBER = 999;

    @Override
    public String generate() {
        Integer numberPlate = ThreadLocalRandom.current()
                .nextInt(MAX_CAR_NUMBER - MIN_CAR_NUMBER) + MIN_CAR_NUMBER;

        return String.format("%03d", numberPlate);
    }
}
