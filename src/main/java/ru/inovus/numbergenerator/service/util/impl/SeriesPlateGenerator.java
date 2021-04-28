package ru.inovus.numbergenerator.service.util.impl;

import org.springframework.stereotype.Component;
import ru.inovus.numbergenerator.service.util.Generator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class SeriesPlateGenerator implements Generator<String> {
    private static final List<String> SERIES = Arrays.asList("А", "В", "С", "Е", "Н", "К", "М", "О", "Р", "Т", "Х", "У");
    private static final int NUMBER_OF_LETTERS_IN_CAR_SERIES = 3;

    @Override
    public String generate() {
        return ThreadLocalRandom.current().ints(0, SERIES.size())
                .limit(NUMBER_OF_LETTERS_IN_CAR_SERIES)
                .mapToObj(SERIES::get)
                .collect(Collectors.joining());
    }
}
