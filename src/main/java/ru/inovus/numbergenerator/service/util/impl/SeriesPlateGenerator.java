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

    @Override
    public String generate() {
        return ThreadLocalRandom.current().ints(0, SERIES.size())
                .limit(3)
                .mapToObj(SERIES::get)
                .collect(Collectors.joining());
    }
}
