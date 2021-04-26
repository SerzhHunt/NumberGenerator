package ru.inovus.numbergenerator.service.impl;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import ru.inovus.numbergenerator.dto.RegistrationNumberDto;
import ru.inovus.numbergenerator.exception.RegistrationNumberNotFoundException;
import ru.inovus.numbergenerator.exception.ValidationRegistrationNumberException;
import ru.inovus.numbergenerator.model.Country;
import ru.inovus.numbergenerator.model.Region;
import ru.inovus.numbergenerator.model.RegistrationNumber;
import ru.inovus.numbergenerator.repository.RegistrationNumberRepository;
import ru.inovus.numbergenerator.service.RegistrationNumberService;
import ru.inovus.numbergenerator.service.util.impl.NumberPlateGenerator;
import ru.inovus.numbergenerator.service.util.impl.SeriesPlateGenerator;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationNumberServiceImpl implements RegistrationNumberService {
    private static final int MAX_CAR_NUMBER = 999;
    private static final List<String> SERIES = Arrays.asList("А", "В", "С", "Е", "Н", "К", "М", "О", "Р", "Т", "Х", "У");
    private static final int FIRST_LETTER_CAR_SERIES = 0;
    private static final int SECOND_LETTER_CAR_SERIES = 4;
    private static final int THIRD_LETTER_CAR_SERIES = 5;

    private final MapperFacade mapper;
    private final RegistrationNumberRepository repository;
    private final NumberPlateGenerator plateGenerator;
    private final SeriesPlateGenerator seriesGenerator;

    @Override
    public RegistrationNumberDto getRandomRegistrationNumber(Region region, Country country) {
        String generatedNumber = getRegistrationNumber(plateGenerator.generate(), seriesGenerator.generate());
        RegistrationNumberDto registrationNumberDto = buildRegistrationNumber(generatedNumber, region, country);

        RegistrationNumber savedNumber = repository.save(mapper.map(registrationNumberDto, RegistrationNumber.class));
        return mapper.map(savedNumber, RegistrationNumberDto.class);
    }

    @Override
    public RegistrationNumberDto getNextRegistrationNumber(Region region, Country country) {
        Optional<RegistrationNumber> regNumber = repository.findFirstByOrderByIdDesc();

        if (regNumber.isEmpty()) {
            throw new RegistrationNumberNotFoundException("");
        }

        RegistrationNumberDto nextRegNumber = generateNextRegistrationNumber(
                mapper.map(regNumber.get(), RegistrationNumberDto.class));

        RegistrationNumber savedNextNumber = repository.save(mapper.map(nextRegNumber, RegistrationNumber.class));

        return mapper.map(savedNextNumber, RegistrationNumberDto.class);
    }

    @Override
    @Transactional
    public RegistrationNumberDto saveRegistrationNumber(RegistrationNumberDto registrationNumberDto) {
        RegistrationNumber savedNumber = repository.save(mapper.map(registrationNumberDto, RegistrationNumber.class));
        return mapper.map(savedNumber, RegistrationNumberDto.class);
    }

    private String getRegistrationNumber(String number, String series) {
        return series.charAt(0) + number + series.substring(1);
    }

    private RegistrationNumberDto generateNextRegistrationNumber(RegistrationNumberDto registrationNumber) {
        return validateCarNumber(registrationNumber);
    }

    private RegistrationNumberDto validateCarNumber(RegistrationNumberDto registrationNumber) {
        int oldCarNumber = Integer.parseInt(registrationNumber.getRegistrationPlate().substring(1, 4));

        if (oldCarNumber < MAX_CAR_NUMBER) {
            int tmp = oldCarNumber;
            int newCarNumber = oldCarNumber += 1;
            oldCarNumber = tmp;

            registrationNumber.setRegistrationPlate(registrationNumber.getRegistrationPlate()
                    .replace(String.valueOf(oldCarNumber), String.valueOf(newCarNumber)));
        } else {
            registrationNumber.setRegistrationPlate(registrationNumber.getRegistrationPlate()
                    .replace(String.valueOf(oldCarNumber), "001"));
            return validateCarSeries(registrationNumber);
        }
        return registrationNumber;
    }

    private RegistrationNumberDto validateCarSeries(RegistrationNumberDto registrationNumber) {
        String firstLetter = String.valueOf(registrationNumber.getRegistrationPlate().charAt(FIRST_LETTER_CAR_SERIES));
        String secondLetter = String.valueOf(registrationNumber.getRegistrationPlate().charAt(SECOND_LETTER_CAR_SERIES));
        String thirdLetter = String.valueOf(registrationNumber.getRegistrationPlate().charAt(THIRD_LETTER_CAR_SERIES));


        for (int i = 0; i < SERIES.size(); i++) {
            int thirdIndex = SERIES.indexOf(thirdLetter);
            if (thirdIndex < SERIES.size()) {
                return changeLetterCarSeries(registrationNumber, thirdIndex, THIRD_LETTER_CAR_SERIES);
            } else {
                int firstIndex = SERIES.indexOf(firstLetter);
                if (firstIndex < SERIES.size()) {
                    return changeLetterCarSeries(registrationNumber, firstIndex, FIRST_LETTER_CAR_SERIES);
                } else {
                    int secondIndex = SERIES.indexOf(secondLetter);
                    if (secondIndex < SERIES.size()) {
                        return changeLetterCarSeries(registrationNumber, secondIndex, SECOND_LETTER_CAR_SERIES);
                    }
                }
            }
        }
        throw new ValidationRegistrationNumberException("Error car series validation");
    }

    private RegistrationNumberDto changeLetterCarSeries(RegistrationNumberDto registrationNumber,
                                                        int index, int letterPosition) {
        String letterCarSeries = String.valueOf(registrationNumber.getRegistrationPlate().charAt(letterPosition));

        registrationNumber.setRegistrationPlate(registrationNumber
                .getRegistrationPlate()
                .replace(letterCarSeries, SERIES.get(index + 1)));

        return registrationNumber;
    }

    private RegistrationNumberDto buildRegistrationNumber(String registrationPlate, Region region, Country country) {
        RegistrationNumberDto registrationNumber = new RegistrationNumberDto();

        registrationNumber.setRegistrationPlate(registrationPlate);
        registrationNumber.setRegion(region.getRegion());
        registrationNumber.setCountry(country);

        return registrationNumber;
    }
}
